# JVM Basics

## What is JDK, JRE, and JVM?

### JDK (Java Development Kit)
The JDK is the complete development environment for Java. It includes:
- **Compiler (javac)**: Converts Java source code (.java files) into bytecode (.class files)
- **JRE**: The runtime environment (included in JDK)
- **Development Tools**: Debugger, documentation generator, and other utilities

**When to use**: You need JDK to **develop** Java applications.

### JRE (Java Runtime Environment)
The JRE is what you need to **run** Java applications. It includes:
- **JVM**: The virtual machine that executes bytecode
- **Core Libraries**: Standard Java classes (java.lang, java.util, etc.)
- **Supporting Files**: Configuration and resource files

**When to use**: End users who only need to run Java programs need JRE.

### JVM (Java Virtual Machine)
The JVM is the engine that executes Java bytecode. It provides:
- **Platform Independence**: Same bytecode runs on any OS with a JVM
- **Memory Management**: Automatic garbage collection
- **Security**: Sandboxed execution environment

**Key Point**: The JVM is **not** the same on every platform - there are different implementations for Windows, macOS, Linux, etc. But they all execute the same bytecode.

## Relationship Diagram

```
┌─────────────────────────────────────────────────┐
│                      JDK                        │
│  ┌───────────────────────────────────────────┐  │
│  │                    JRE                    │  │
│  │  ┌─────────────────────────────────────┐  │  │
│  │  │               JVM                   │  │  │
│  │  │  - Class Loader                     │  │  │
│  │  │  - Bytecode Verifier                │  │  │
│  │  │  - Execution Engine                 │  │  │
│  │  │  - Garbage Collector                │  │  │
│  │  └─────────────────────────────────────┘  │  │
│  │  + Core Libraries                         │  │
│  └───────────────────────────────────────────┘  │
│  + Compiler (javac)                             │
│  + Debugger                                     │
│  + Other Dev Tools                              │
└─────────────────────────────────────────────────┘
```

## What is Bytecode?

Bytecode is the intermediate representation of Java code:

1. **Source Code** (.java) → Human-readable Java code
2. **Compilation** (javac) → Converts source to bytecode
3. **Bytecode** (.class) → Platform-independent instructions
4. **Execution** (JVM) → Interprets/compiles bytecode to machine code

### Example:
```java
// Source Code: HelloWorld.java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello!");
    }
}
```

After `javac HelloWorld.java`:
```
// Bytecode (simplified view)
public static void main(java.lang.String[]);
    Code:
       0: getstatic     #2    // Field java/lang/System.out
       3: ldc           #3    // String Hello!
       5: invokevirtual #4    // Method java/io/PrintStream.println
       8: return
```

## Write Once, Run Anywhere (WORA)

"Write Once, Run Anywhere" is Java's promise of platform independence:

### How It Works:
1. **Write** your Java code once on any platform (Windows, Mac, Linux)
2. **Compile** it once to create bytecode (.class files)
3. **Run** the same bytecode on ANY platform that has a JVM

### Why It Works:
- The **bytecode is identical** regardless of where it was compiled
- Each platform has its **own JVM implementation** that knows how to:
  - Translate bytecode to that platform's native machine code
  - Handle platform-specific operations (file I/O, networking, etc.)

### Real-World Example:
```
Developer's Mac                    User's Windows PC
┌─────────────┐                   ┌─────────────────┐
│ Main.java   │                   │                 │
│     ↓       │                   │  Main.class     │
│   javac     │     transfer      │     ↓           │
│     ↓       │  ───────────────→ │  Windows JVM    │
│ Main.class  │                   │     ↓           │
└─────────────┘                   │  Program runs!  │
                                  └─────────────────┘
```

### Benefits:
- **Developers**: Write code once, deploy everywhere
- **Users**: Same application runs on their preferred OS
- **Organizations**: Reduced development and maintenance costs

### Limitations:
- Some platform-specific features may not be available
- JVM must be installed on the target machine
- Performance may vary slightly between platforms
