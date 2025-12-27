# Design Notes

## Architecture Overview

LearnTrack follows a layered architecture pattern with clear separation of concerns:

```
┌─────────────────────────────────────────────────────┐
│                    Main.java                        │
│                 (Presentation Layer)                │
│              Menu display, user input               │
└─────────────────────────┬───────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────┐
│                   Service Layer                     │
│     StudentService, CourseService, EnrollmentService│
│          Business logic, validation                 │
└─────────────────────────┬───────────────────────────┘
                          │
┌─────────────────────────▼───────────────────────────┐
│                  Repository Layer                   │
│   StudentRepository, CourseRepository, etc.         │
│              Data storage (ArrayList)               │
└─────────────────────────────────────────────────────┘
```

## Why ArrayList Instead of Array?

We chose `ArrayList` over primitive arrays for several reasons:

### 1. Dynamic Sizing
```java
// Array - Fixed size, must know count in advance
Student[] students = new Student[100]; // What if we need 101?

// ArrayList - Grows automatically
ArrayList<Student> students = new ArrayList<>(); // No limit!
```

### 2. Built-in Methods
ArrayList provides convenient methods:
- `add()` - Add elements easily
- `remove()` - Remove by object or index
- `get()` - Access by index
- `size()` - Get current count
- `contains()` - Check if element exists

### 3. Type Safety with Generics
```java
ArrayList<Student> students = new ArrayList<>();
students.add(new Student(...)); // OK
students.add(new Course(...));  // Compile error! Type safety.
```

### 4. No Null Gaps
With arrays, deleting elements leaves null gaps. ArrayList handles this automatically.

## Where We Used Static Members and Why

### IdGenerator Utility Class
```java
public class IdGenerator {
    private static int studentIdCounter = 0;

    public static int getNextStudentId() {
        return ++studentIdCounter;
    }
}
```

**Why Static?**
- Only ONE counter should exist for all students (singleton behavior)
- No need to create IdGenerator instances
- Counter persists across all service calls
- Ensures globally unique IDs

### AppConstants Class
```java
public class AppConstants {
    public static final String APP_NAME = "LearnTrack";
    public static final String WELCOME_MESSAGE = "...";
}
```

**Why Static?**
- Constants don't change, no instance needed
- Access from anywhere: `AppConstants.APP_NAME`
- Memory efficient (one copy in memory)

## Where We Used Inheritance and What We Gained

### Person → Student Hierarchy
```java
public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public String getDisplayName() {
        return firstName + " " + lastName;
    }
}

public class Student extends Person {
    private String batch;
    private boolean active;

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }
}
```

### Benefits Gained:

1. **Code Reuse**: Common fields (id, name, email) defined once in Person
2. **Extensibility**: Easy to add `Trainer extends Person` later
3. **Polymorphism**: Can use `Person` reference for any subclass
4. **Method Overriding**: Student customizes `getDisplayName()` behavior

### The `super` Keyword
```java
public Student(int id, String firstName, String lastName, String email, String batch) {
    super(id, firstName, lastName, email);  // Calls Person constructor
    this.batch = batch;
}
```

## Exception Handling Strategy

### Custom Exceptions
```java
public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityType, int id) {
        super(entityType + " with ID " + id + " not found");
    }
}
```

**Why Custom Exceptions?**
- More meaningful error messages
- Easier to catch specific error types
- Self-documenting code

### Try-Catch Usage
```java
try {
    int id = Integer.parseInt(scanner.nextLine());
    Student student = studentService.getStudentById(id);
} catch (NumberFormatException e) {
    System.out.println("Please enter a valid number.");
} catch (EntityNotFoundException e) {
    System.out.println("Error: " + e.getMessage());
}
```

## Encapsulation in Practice

All entity fields are private with controlled access:

```java
public class Student {
    private String firstName;  // Private field

    // Controlled getter
    public String getFirstName() {
        return firstName;
    }

    // Controlled setter with potential validation
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }
}
```

**Benefits:**
- Hide internal implementation
- Add validation in setters if needed
- Change internal representation without affecting users

## Constructor Overloading Examples

```java
// Multiple constructors for flexibility
public Student() { }  // Default

public Student(int id, String firstName, String lastName, String batch) {
    // Without email
}

public Student(int id, String firstName, String lastName, String email, String batch) {
    // With email
}

public Student(int id, String firstName, String lastName, String email, String batch, boolean active) {
    // Full constructor
}
```

This allows creating students with different levels of information.

## Clean Code Principles Applied

1. **Single Responsibility**: Each class has one job
   - Entity: Hold data
   - Repository: Store/retrieve data
   - Service: Business logic
   - Main: User interaction

2. **Meaningful Names**:
   - `addStudent()` not `add()` or `process()`
   - `findById()` not `find()` or `get()`

3. **Small Methods**: Each method does one thing

4. **Consistent Formatting**: Standard indentation and structure

5. **Comments Where Needed**: Explain "why", not "what"
