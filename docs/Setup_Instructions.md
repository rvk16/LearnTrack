# Setup Instructions

## Prerequisites

### JDK Version
This project requires **JDK 17** or higher. The project uses features such as:
- Text blocks (multi-line strings)
- Enhanced switch expressions
- Modern Java syntax

### Verifying Java Installation

Open a terminal and run:
```bash
java -version
javac -version
```

You should see output similar to:
```
java version "17.0.x" or higher
```

## Installation Steps

### 1. Download and Install JDK

If you don't have JDK installed:

**Option A: Oracle JDK**
- Download from: https://www.oracle.com/java/technologies/downloads/

**Option B: OpenJDK (Recommended)**
- Download from: https://adoptium.net/

### 2. Set JAVA_HOME Environment Variable

**Windows:**
```cmd
setx JAVA_HOME "C:\Program Files\Java\jdk-17"
setx PATH "%PATH%;%JAVA_HOME%\bin"
```

**macOS/Linux:**
Add to `~/.bashrc` or `~/.zshrc`:
```bash
export JAVA_HOME=/path/to/jdk-17
export PATH=$JAVA_HOME/bin:$PATH
```

### 3. Verify Setup

Run the following commands to verify:
```bash
java -version
javac -version
echo $JAVA_HOME   # or echo %JAVA_HOME% on Windows
```

## Compiling the Project

### Method 1: Command Line

```bash
# Navigate to project directory
cd LearnTrack

# Create output directory
mkdir -p out

# Compile all source files
javac -d out -sourcepath src src/com/airtribe/learntrack/Main.java

# Run the application
java -cp out com.airtribe.learntrack.Main
```

### Method 2: Using an IDE

**IntelliJ IDEA:**
1. File > Open > Select the LearnTrack folder
2. Right-click on `src` folder > Mark Directory as > Sources Root
3. Right-click on `Main.java` > Run 'Main'

**Eclipse:**
1. File > Import > General > Existing Projects into Workspace
2. Select the LearnTrack folder
3. Right-click on `Main.java` > Run As > Java Application

**VS Code:**
1. Open the LearnTrack folder
2. Install "Extension Pack for Java" extension
3. Open `Main.java` and click "Run" above the main method

## Hello World Verification

A simple test to verify your Java setup:

1. Create a file `HelloWorld.java`:
```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, World!");
        System.out.println("Java Version: " + System.getProperty("java.version"));
        System.out.println("Java Home: " + System.getProperty("java.home"));
    }
}
```

2. Compile and run:
```bash
javac HelloWorld.java
java HelloWorld
```

Expected output:
```
Hello, World!
Java Version: 17.x.x
Java Home: /path/to/jdk-17
```

## Troubleshooting

### Common Issues

1. **'javac' is not recognized**
   - Ensure JAVA_HOME is set correctly
   - Ensure %JAVA_HOME%\bin is in your PATH

2. **Class not found error**
   - Make sure you're running from the correct directory
   - Verify the classpath is set correctly

3. **Unsupported class file version**
   - You're trying to run code compiled with a newer JDK
   - Upgrade your JRE/JDK to match the compiled version
