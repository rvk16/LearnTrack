package com.airtribe.learntrack;

import com.airtribe.learntrack.constants.AppConstants;
import com.airtribe.learntrack.constants.MenuOptions;
import com.airtribe.learntrack.entity.Course;
import com.airtribe.learntrack.entity.Enrollment;
import com.airtribe.learntrack.entity.Student;
import com.airtribe.learntrack.enums.EnrollmentStatus;
import com.airtribe.learntrack.exception.EntityNotFoundException;
import com.airtribe.learntrack.exception.InvalidInputException;
import com.airtribe.learntrack.repository.CourseRepository;
import com.airtribe.learntrack.repository.EnrollmentRepository;
import com.airtribe.learntrack.repository.StudentRepository;
import com.airtribe.learntrack.service.CourseService;
import com.airtribe.learntrack.service.EnrollmentService;
import com.airtribe.learntrack.service.StudentService;

import java.util.List;
import java.util.Scanner;

/**
 * Main application entry point.
 * Provides a menu-driven console interface for managing students, courses, and enrollments.
 */
public class Main {
    // Services
    private static StudentService studentService;
    private static CourseService courseService;
    private static EnrollmentService enrollmentService;

    // Scanner for user input
    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        // Initialize repositories
        StudentRepository studentRepository = new StudentRepository();
        CourseRepository courseRepository = new CourseRepository();
        EnrollmentRepository enrollmentRepository = new EnrollmentRepository();

        // Initialize services
        studentService = new StudentService(studentRepository);
        courseService = new CourseService(courseRepository);
        enrollmentService = new EnrollmentService(enrollmentRepository, studentService, courseService);

        // Display welcome message
        System.out.println(AppConstants.LINE_SEPARATOR);
        System.out.println(AppConstants.WELCOME_MESSAGE);
        System.out.println(AppConstants.LINE_SEPARATOR);

        // Add some sample data
        addSampleData();

        // Run main menu loop
        runMainMenu();

        // Cleanup
        scanner.close();
        System.out.println(AppConstants.GOODBYE_MESSAGE);
    }

    /**
     * Adds sample data for demonstration purposes.
     */
    private static void addSampleData() {
        try {
            // Add sample students
            studentService.addStudent("Rahul", "Vishwakarma", "rahul.vishwakarma@gmail.com", "Batch-2025A");
            studentService.addStudent("Ram", "Kumar", "ram.kumar@email.com", "Batch-2025A");
            studentService.addStudent("Raj", "Kumar", "raj.kumar@email.com", "Batch-2025B");

            // Add sample courses
            courseService.addCourse("Java Fundamentals", "Introduction to Java programming", 8);
            courseService.addCourse("Data Structures", "Learn about arrays, lists, trees, and graphs", 10);
            courseService.addCourse("Web Development", "HTML, CSS, and JavaScript basics", 6);

            // Add sample enrollments
            enrollmentService.enrollStudent(1, 1); // John in Java Fundamentals
            enrollmentService.enrollStudent(2, 1); // Jane in Java Fundamentals
            enrollmentService.enrollStudent(1, 2); // John in Data Structures

            System.out.println("Sample data loaded successfully!\n");
        } catch (Exception e) {
            System.out.println("Note: Could not load sample data - " + e.getMessage());
        }
    }

    /**
     * Main menu loop.
     */
    private static void runMainMenu() {
        boolean running = true;

        while (running) {
            System.out.println(MenuOptions.MAIN_MENU);
            System.out.print(AppConstants.ENTER_CHOICE_PROMPT);

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case MenuOptions.MAIN_STUDENT_MANAGEMENT:
                        runStudentMenu();
                        break;
                    case MenuOptions.MAIN_COURSE_MANAGEMENT:
                        runCourseMenu();
                        break;
                    case MenuOptions.MAIN_ENROLLMENT_MANAGEMENT:
                        runEnrollmentMenu();
                        break;
                    case MenuOptions.MAIN_EXIT:
                        running = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    // ==================== STUDENT MANAGEMENT ====================

    /**
     * Student management menu loop.
     */
    private static void runStudentMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println(MenuOptions.STUDENT_MENU);
            System.out.print(AppConstants.ENTER_CHOICE_PROMPT);

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case MenuOptions.STUDENT_ADD:
                        addNewStudent();
                        break;
                    case MenuOptions.STUDENT_VIEW_ALL:
                        viewAllStudents();
                        break;
                    case MenuOptions.STUDENT_SEARCH_BY_ID:
                        searchStudentById();
                        break;
                    case MenuOptions.STUDENT_UPDATE:
                        updateStudent();
                        break;
                    case MenuOptions.STUDENT_DEACTIVATE:
                        deactivateStudent();
                        break;
                    case MenuOptions.STUDENT_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addNewStudent() {
        System.out.println("\n--- Add New Student ---");
        try {
            System.out.print("First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Email (optional, press Enter to skip): ");
            String email = scanner.nextLine();

            System.out.print("Batch: ");
            String batch = scanner.nextLine();

            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("\nStudent added successfully!");
            System.out.println(student);
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println(AppConstants.NO_STUDENTS_MESSAGE);
        } else {
            System.out.println(AppConstants.THIN_SEPARATOR);
            for (Student student : students) {
                printStudentDetails(student);
                System.out.println(AppConstants.THIN_SEPARATOR);
            }
            System.out.println("Total: " + students.size() + " student(s)");
        }
        pressEnterToContinue();
    }

    private static void searchStudentById() {
        System.out.println("\n--- Search Student by ID ---");
        try {
            System.out.print("Enter Student ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentService.getStudentById(id);
            System.out.println("\nStudent found:");
            printStudentDetails(student);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void updateStudent() {
        System.out.println("\n--- Update Student ---");
        try {
            System.out.print("Enter Student ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentService.getStudentById(id);
            System.out.println("Current details:");
            printStudentDetails(student);

            System.out.println("\nEnter new values (press Enter to keep current value):");

            System.out.print("First Name [" + student.getFirstName() + "]: ");
            String firstName = scanner.nextLine();

            System.out.print("Last Name [" + student.getLastName() + "]: ");
            String lastName = scanner.nextLine();

            System.out.print("Email [" + student.getEmail() + "]: ");
            String email = scanner.nextLine();

            System.out.print("Batch [" + student.getBatch() + "]: ");
            String batch = scanner.nextLine();

            Student updated = studentService.updateStudent(id,
                    firstName.isEmpty() ? null : firstName,
                    lastName.isEmpty() ? null : lastName,
                    email.isEmpty() ? null : email,
                    batch.isEmpty() ? null : batch);

            System.out.println("\nStudent updated successfully!");
            printStudentDetails(updated);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");
        try {
            System.out.print("Enter Student ID to deactivate: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentService.deactivateStudent(id);
            System.out.println("\nStudent deactivated successfully!");
            printStudentDetails(student);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void printStudentDetails(Student student) {
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getDisplayName());
        System.out.println("Email: " + (student.getEmail().isEmpty() ? "N/A" : student.getEmail()));
        System.out.println("Batch: " + student.getBatch());
        System.out.println("Status: " + (student.isActive() ? "Active" : "Inactive"));
    }

    // ==================== COURSE MANAGEMENT ====================

    /**
     * Course management menu loop.
     */
    private static void runCourseMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println(MenuOptions.COURSE_MENU);
            System.out.print(AppConstants.ENTER_CHOICE_PROMPT);

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case MenuOptions.COURSE_ADD:
                        addNewCourse();
                        break;
                    case MenuOptions.COURSE_VIEW_ALL:
                        viewAllCourses();
                        break;
                    case MenuOptions.COURSE_SEARCH_BY_ID:
                        searchCourseById();
                        break;
                    case MenuOptions.COURSE_UPDATE:
                        updateCourse();
                        break;
                    case MenuOptions.COURSE_TOGGLE_STATUS:
                        toggleCourseStatus();
                        break;
                    case MenuOptions.COURSE_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void addNewCourse() {
        System.out.println("\n--- Add New Course ---");
        try {
            System.out.print("Course Name: ");
            String courseName = scanner.nextLine();

            System.out.print("Description (optional): ");
            String description = scanner.nextLine();

            System.out.print("Duration in Weeks: ");
            int duration = Integer.parseInt(scanner.nextLine().trim());

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("\nCourse added successfully!");
            System.out.println(course);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for duration.");
        } catch (InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println(AppConstants.NO_COURSES_MESSAGE);
        } else {
            System.out.println(AppConstants.THIN_SEPARATOR);
            for (Course course : courses) {
                printCourseDetails(course);
                System.out.println(AppConstants.THIN_SEPARATOR);
            }
            System.out.println("Total: " + courses.size() + " course(s)");
        }
        pressEnterToContinue();
    }

    private static void searchCourseById() {
        System.out.println("\n--- Search Course by ID ---");
        try {
            System.out.print("Enter Course ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Course course = courseService.getCourseById(id);
            System.out.println("\nCourse found:");
            printCourseDetails(course);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void updateCourse() {
        System.out.println("\n--- Update Course ---");
        try {
            System.out.print("Enter Course ID to update: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Course course = courseService.getCourseById(id);
            System.out.println("Current details:");
            printCourseDetails(course);

            System.out.println("\nEnter new values (press Enter to keep current value):");

            System.out.print("Course Name [" + course.getCourseName() + "]: ");
            String courseName = scanner.nextLine();

            System.out.print("Description [" + course.getDescription() + "]: ");
            String description = scanner.nextLine();

            System.out.print("Duration in Weeks [" + course.getDurationInWeeks() + "]: ");
            String durationStr = scanner.nextLine();
            // Use -1 to indicate no change (service ignores values <= 0)
            int duration = durationStr.isEmpty() ? -1 : Integer.parseInt(durationStr.trim());

            Course updated = courseService.updateCourse(id,
                    courseName.isEmpty() ? null : courseName,
                    description.isEmpty() ? null : description,
                    duration);

            System.out.println("\nCourse updated successfully!");
            printCourseDetails(updated);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void toggleCourseStatus() {
        System.out.println("\n--- Activate/Deactivate Course ---");
        try {
            System.out.print("Enter Course ID: ");
            int id = Integer.parseInt(scanner.nextLine().trim());

            Course course = courseService.toggleCourseStatus(id);
            String status = course.isActive() ? "activated" : "deactivated";
            System.out.println("\nCourse " + status + " successfully!");
            printCourseDetails(course);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void printCourseDetails(Course course) {
        System.out.println("ID: " + course.getId());
        System.out.println("Name: " + course.getCourseName());
        System.out.println("Description: " + (course.getDescription().isEmpty() ? "N/A" : course.getDescription()));
        System.out.println("Duration: " + course.getDurationInWeeks() + " weeks");
        System.out.println("Status: " + (course.isActive() ? "Active" : "Inactive"));
    }

    // ==================== ENROLLMENT MANAGEMENT ====================

    /**
     * Enrollment management menu loop.
     */
    private static void runEnrollmentMenu() {
        boolean inMenu = true;

        while (inMenu) {
            System.out.println(MenuOptions.ENROLLMENT_MENU);
            System.out.print(AppConstants.ENTER_CHOICE_PROMPT);

            try {
                int choice = Integer.parseInt(scanner.nextLine().trim());

                switch (choice) {
                    case MenuOptions.ENROLLMENT_ENROLL:
                        enrollStudent();
                        break;
                    case MenuOptions.ENROLLMENT_VIEW_BY_STUDENT:
                        viewEnrollmentsByStudent();
                        break;
                    case MenuOptions.ENROLLMENT_VIEW_BY_COURSE:
                        viewEnrollmentsByCourse();
                        break;
                    case MenuOptions.ENROLLMENT_UPDATE_STATUS:
                        updateEnrollmentStatus();
                        break;
                    case MenuOptions.ENROLLMENT_VIEW_ALL:
                        viewAllEnrollments();
                        break;
                    case MenuOptions.ENROLLMENT_BACK:
                        inMenu = false;
                        break;
                    default:
                        System.out.println(AppConstants.INVALID_OPTION_MESSAGE);
                }
            } catch (NumberFormatException e) {
                System.out.println("Please enter a valid number.");
            }
        }
    }

    private static void enrollStudent() {
        System.out.println("\n--- Enroll Student in Course ---");
        try {
            // Show available students
            System.out.println("Available Students:");
            List<Student> students = studentService.getActiveStudents();
            for (Student s : students) {
                System.out.println("  ID " + s.getId() + ": " + s.getDisplayName());
            }

            System.out.print("\nEnter Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());

            // Show available courses
            System.out.println("\nAvailable Courses:");
            List<Course> courses = courseService.getActiveCourses();
            for (Course c : courses) {
                System.out.println("  ID " + c.getId() + ": " + c.getCourseName());
            }

            System.out.print("\nEnter Course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());

            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId);
            System.out.println("\nEnrollment successful!");
            printEnrollmentDetails(enrollment);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException | InvalidInputException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void viewEnrollmentsByStudent() {
        System.out.println("\n--- View Enrollments by Student ---");
        try {
            System.out.print("Enter Student ID: ");
            int studentId = Integer.parseInt(scanner.nextLine().trim());

            Student student = studentService.getStudentById(studentId);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudent(studentId);

            System.out.println("\nEnrollments for " + student.getDisplayName() + ":");
            if (enrollments.isEmpty()) {
                System.out.println(AppConstants.NO_ENROLLMENTS_MESSAGE);
            } else {
                System.out.println(AppConstants.THIN_SEPARATOR);
                for (Enrollment enrollment : enrollments) {
                    printEnrollmentDetails(enrollment);
                    System.out.println(AppConstants.THIN_SEPARATOR);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void viewEnrollmentsByCourse() {
        System.out.println("\n--- View Enrollments by Course ---");
        try {
            System.out.print("Enter Course ID: ");
            int courseId = Integer.parseInt(scanner.nextLine().trim());

            Course course = courseService.getCourseById(courseId);
            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourse(courseId);

            System.out.println("\nEnrollments for " + course.getCourseName() + ":");
            if (enrollments.isEmpty()) {
                System.out.println(AppConstants.NO_ENROLLMENTS_MESSAGE);
            } else {
                System.out.println(AppConstants.THIN_SEPARATOR);
                for (Enrollment enrollment : enrollments) {
                    printEnrollmentDetails(enrollment);
                    System.out.println(AppConstants.THIN_SEPARATOR);
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void updateEnrollmentStatus() {
        System.out.println("\n--- Update Enrollment Status ---");
        try {
            System.out.print("Enter Enrollment ID: ");
            int enrollmentId = Integer.parseInt(scanner.nextLine().trim());

            Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
            System.out.println("Current status: " + enrollment.getStatus().getDisplayName());

            System.out.println("\nSelect new status:");
            System.out.println("1. Active");
            System.out.println("2. Completed");
            System.out.println("3. Cancelled");
            System.out.print("Choice: ");

            int statusChoice = Integer.parseInt(scanner.nextLine().trim());
            EnrollmentStatus newStatus;

            switch (statusChoice) {
                case 1:
                    newStatus = EnrollmentStatus.ACTIVE;
                    break;
                case 2:
                    newStatus = EnrollmentStatus.COMPLETED;
                    break;
                case 3:
                    newStatus = EnrollmentStatus.CANCELLED;
                    break;
                default:
                    System.out.println("Invalid status choice.");
                    return;
            }

            Enrollment updated = enrollmentService.updateEnrollmentStatus(enrollmentId, newStatus);
            System.out.println("\nEnrollment status updated successfully!");
            printEnrollmentDetails(updated);
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        } catch (EntityNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
        }
        pressEnterToContinue();
    }

    private static void viewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println(AppConstants.NO_ENROLLMENTS_MESSAGE);
        } else {
            System.out.println(AppConstants.THIN_SEPARATOR);
            for (Enrollment enrollment : enrollments) {
                printEnrollmentDetails(enrollment);
                System.out.println(AppConstants.THIN_SEPARATOR);
            }
            System.out.println("Total: " + enrollments.size() + " enrollment(s)");
        }
        pressEnterToContinue();
    }

    private static void printEnrollmentDetails(Enrollment enrollment) {
        System.out.println("Enrollment ID: " + enrollment.getId());
        try {
            Student student = studentService.getStudentById(enrollment.getStudentId());
            System.out.println("Student: " + student.getDisplayName() + " (ID: " + student.getId() + ")");
        } catch (EntityNotFoundException e) {
            System.out.println("Student ID: " + enrollment.getStudentId() + " (not found)");
        }
        try {
            Course course = courseService.getCourseById(enrollment.getCourseId());
            System.out.println("Course: " + course.getCourseName() + " (ID: " + course.getId() + ")");
        } catch (EntityNotFoundException e) {
            System.out.println("Course ID: " + enrollment.getCourseId() + " (not found)");
        }
        System.out.println("Enrollment Date: " + enrollment.getEnrollmentDate());
        System.out.println("Status: " + enrollment.getStatus().getDisplayName());
    }

    // ==================== UTILITY METHODS ====================

    private static void pressEnterToContinue() {
        System.out.println("\n" + AppConstants.PRESS_ENTER_PROMPT);
        scanner.nextLine();
    }
}
