# LearnTrack - Student & Course Management System

LearnTrack is a console-based Student & Course Management System built using Core Java. It allows administrators to manage students, courses, and enrollments through an interactive menu-driven interface.

## Features

- **Student Management**: Add, view, search, update, and deactivate students
- **Course Management**: Add, view, search, update, and toggle course status
- **Enrollment Management**: Enroll students in courses, view enrollments, update enrollment status

## Class Diagram

### Entity Classes

```mermaid
classDiagram
    class Person {
        -int id
        -String firstName
        -String lastName
        -String email
        +Person()
        +Person(id, firstName, lastName, email)
        +getDisplayName() String
        +getters/setters()
    }

    class Student {
        -String batch
        -boolean active
        +Student()
        +Student(id, firstName, lastName, email, batch)
        +Student(id, firstName, lastName, batch)
        +getDisplayName() String
        +getters/setters()
    }

    class Course {
        -int id
        -String courseName
        -String description
        -int durationInWeeks
        -boolean active
        +Course()
        +Course(id, courseName, description, durationInWeeks)
        +getters/setters()
    }

    class Enrollment {
        -int id
        -int studentId
        -int courseId
        -LocalDate enrollmentDate
        -EnrollmentStatus status
        +Enrollment()
        +Enrollment(id, studentId, courseId)
        +getters/setters()
    }

    class EnrollmentStatus {
        <<enumeration>>
        ACTIVE
        COMPLETED
        CANCELLED
        +getDisplayName() String
    }

    Person <|-- Student : extends
    Enrollment --> EnrollmentStatus : uses
```

### Repository Layer

```mermaid
classDiagram
    class StudentRepository {
        -List~Student~ students
        +save(Student)
        +findById(int) Optional~Student~
        +findAll() List~Student~
        +findAllActive() List~Student~
        +update(Student) boolean
        +deleteById(int) boolean
        +existsById(int) boolean
    }

    class CourseRepository {
        -List~Course~ courses
        +save(Course)
        +findById(int) Optional~Course~
        +findAll() List~Course~
        +findAllActive() List~Course~
        +update(Course) boolean
        +deleteById(int) boolean
    }

    class EnrollmentRepository {
        -List~Enrollment~ enrollments
        +save(Enrollment)
        +findById(int) Optional~Enrollment~
        +findByStudentId(int) List~Enrollment~
        +findByCourseId(int) List~Enrollment~
        +findByStatus(EnrollmentStatus) List~Enrollment~
        +update(Enrollment) boolean
    }

    StudentRepository --> Student : stores
    CourseRepository --> Course : stores
    EnrollmentRepository --> Enrollment : stores
```

### Service Layer

```mermaid
classDiagram
    class StudentService {
        -StudentRepository studentRepository
        +addStudent(firstName, lastName, email, batch) Student
        +addStudent(firstName, lastName, batch) Student
        +getStudentById(int) Student
        +getAllStudents() List~Student~
        +updateStudent(...) Student
        +deactivateStudent(int) Student
    }

    class CourseService {
        -CourseRepository courseRepository
        +addCourse(name, description, duration) Course
        +getCourseById(int) Course
        +getAllCourses() List~Course~
        +updateCourse(...) Course
        +toggleCourseStatus(int) Course
    }

    class EnrollmentService {
        -EnrollmentRepository enrollmentRepository
        -StudentService studentService
        -CourseService courseService
        +enrollStudent(studentId, courseId) Enrollment
        +getEnrollmentsByStudent(int) List~Enrollment~
        +getEnrollmentsByCourse(int) List~Enrollment~
        +updateEnrollmentStatus(id, status) Enrollment
    }

    StudentService --> StudentRepository : uses
    CourseService --> CourseRepository : uses
    EnrollmentService --> EnrollmentRepository : uses
    EnrollmentService --> StudentService : validates with
    EnrollmentService --> CourseService : validates with
```

### Application Architecture

```mermaid
classDiagram
    class Main {
        -StudentService studentService$
        -CourseService courseService$
        -EnrollmentService enrollmentService$
        -Scanner scanner$
        +main(String[] args)$
        -runMainMenu()$
        -runStudentMenu()$
        -runCourseMenu()$
        -runEnrollmentMenu()$
    }

    class IdGenerator {
        <<utility>>
        -int studentIdCounter$
        -int courseIdCounter$
        -int enrollmentIdCounter$
        +getNextStudentId()$ int
        +getNextCourseId()$ int
        +getNextEnrollmentId()$ int
    }

    class InputValidator {
        <<utility>>
        +validateNotEmpty(String, String)$
        +validateEmail(String)$
        +validatePositive(int, String)$
        +parseInteger(String, String)$ int
    }

    class EntityNotFoundException {
        <<exception>>
        +EntityNotFoundException(String)
        +EntityNotFoundException(String, int)
    }

    class InvalidInputException {
        <<exception>>
        +InvalidInputException(String)
        +InvalidInputException(String, String)
    }

    Main --> StudentService : uses
    Main --> CourseService : uses
    Main --> EnrollmentService : uses
```

### Complete System Overview

```mermaid
flowchart TB
    subgraph Presentation["Presentation Layer"]
        Main[Main.java<br/>Console UI & Menus]
    end

    subgraph Business["Business Logic Layer"]
        SS[StudentService]
        CS[CourseService]
        ES[EnrollmentService]
    end

    subgraph Data["Data Access Layer"]
        SR[StudentRepository]
        CR[CourseRepository]
        ER[EnrollmentRepository]
    end

    subgraph Storage["In-Memory Storage"]
        AL1[(ArrayList&lt;Student&gt;)]
        AL2[(ArrayList&lt;Course&gt;)]
        AL3[(ArrayList&lt;Enrollment&gt;)]
    end

    Main --> SS
    Main --> CS
    Main --> ES

    SS --> SR
    CS --> CR
    ES --> ER
    ES -.-> SS
    ES -.-> CS

    SR --> AL1
    CR --> AL2
    ER --> AL3
```

## Project Structure

```
src/
└── com/
    └── airtribe/
        └── learntrack/
            ├── Main.java                    # Menu & application entry point
            ├── entity/                      # Data models
            │   ├── Person.java
            │   ├── Student.java
            │   ├── Course.java
            │   └── Enrollment.java
            ├── repository/                  # Data storage layer (in-memory)
            │   ├── StudentRepository.java
            │   ├── CourseRepository.java
            │   └── EnrollmentRepository.java
            ├── service/                     # Business logic layer
            │   ├── StudentService.java
            │   ├── CourseService.java
            │   └── EnrollmentService.java
            ├── exception/                   # Custom exceptions
            │   ├── EntityNotFoundException.java
            │   └── InvalidInputException.java
            ├── util/                        # Utility classes
            │   ├── IdGenerator.java
            │   └── InputValidator.java
            ├── constants/                   # Application constants
            │   ├── MenuOptions.java
            │   └── AppConstants.java
            └── enums/                       # Enumerations
                └── EnrollmentStatus.java
```

## Prerequisites

- Java Development Kit (JDK) 17 or higher
- Any Java IDE (IntelliJ IDEA, Eclipse, VS Code) or command line

## How to Compile and Run

### Using Command Line

1. Navigate to the project root directory:
   ```bash
   cd LearnTrack
   ```

2. Compile all Java files:
   ```bash
   mkdir -p out
   javac -d out $(find src -name "*.java")
   ```

3. Run the application:
   ```bash
   java -cp out com.airtribe.learntrack.Main
   ```

### Using an IDE

1. Open the `LearnTrack` folder as a project in your IDE
2. Set `src` as the source root
3. Run `Main.java`

## Usage

When you run the application, you'll see a main menu with the following options:

1. **Student Management** - Manage student records
2. **Course Management** - Manage course offerings
3. **Enrollment Management** - Manage student enrollments in courses
0. **Exit** - Exit the application

Each submenu provides specific operations for that entity type.

## OOP Concepts Demonstrated

| Concept | Implementation |
|---------|----------------|
| **Encapsulation** | Private fields with public getters/setters in entity classes |
| **Inheritance** | `Student` extends `Person` base class |
| **Polymorphism** | Method overriding (`getDisplayName()` in Student) |
| **Constructor Overloading** | Multiple constructors in entity and service classes |
| **Static Members** | `IdGenerator` utility class with static counters and methods |

## Technologies Used

- Core Java (JDK 17+)
- Collections Framework (ArrayList)
- Java Time API (LocalDate)
- Exception Handling

## Documentation

See the `docs/` folder for additional documentation:
- `Setup_Instructions.md` - Detailed setup guide
- `JVM_Basics.md` - Understanding JDK, JRE, and JVM
- `Design_Notes.md` - Design decisions and architecture notes

## Author

Rahul Vishwakarma
