package com.airtribe.learntrack.constants;

/**
 * Constants for menu options displayed in the console UI.
 */
public final class MenuOptions {

    // Private constructor to prevent instantiation
    private MenuOptions() {
    }

    // Main Menu Options
    public static final int MAIN_STUDENT_MANAGEMENT = 1;
    public static final int MAIN_COURSE_MANAGEMENT = 2;
    public static final int MAIN_ENROLLMENT_MANAGEMENT = 3;
    public static final int MAIN_EXIT = 0;

    // Student Menu Options
    public static final int STUDENT_ADD = 1;
    public static final int STUDENT_VIEW_ALL = 2;
    public static final int STUDENT_SEARCH_BY_ID = 3;
    public static final int STUDENT_UPDATE = 4;
    public static final int STUDENT_DEACTIVATE = 5;
    public static final int STUDENT_BACK = 0;

    // Course Menu Options
    public static final int COURSE_ADD = 1;
    public static final int COURSE_VIEW_ALL = 2;
    public static final int COURSE_SEARCH_BY_ID = 3;
    public static final int COURSE_UPDATE = 4;
    public static final int COURSE_TOGGLE_STATUS = 5;
    public static final int COURSE_BACK = 0;

    // Enrollment Menu Options
    public static final int ENROLLMENT_ENROLL = 1;
    public static final int ENROLLMENT_VIEW_BY_STUDENT = 2;
    public static final int ENROLLMENT_VIEW_BY_COURSE = 3;
    public static final int ENROLLMENT_UPDATE_STATUS = 4;
    public static final int ENROLLMENT_VIEW_ALL = 5;
    public static final int ENROLLMENT_BACK = 0;

    // Menu Display Strings
    public static final String MAIN_MENU = """

            ==================== MAIN MENU ====================
            1. Student Management
            2. Course Management
            3. Enrollment Management
            0. Exit
            ===================================================
            """;

    public static final String STUDENT_MENU = """

            ================ STUDENT MANAGEMENT ================
            1. Add New Student
            2. View All Students
            3. Search Student by ID
            4. Update Student
            5. Deactivate Student
            0. Back to Main Menu
            ===================================================
            """;

    public static final String COURSE_MENU = """
           
            ================= COURSE MANAGEMENT =================
            1. Add New Course
            2. View All Courses
            3. Search Course by ID
            4. Update Course
            5. Activate/Deactivate Course
            0. Back to Main Menu
            ====================================================
            """;

    public static final String ENROLLMENT_MENU = """

            ============== ENROLLMENT MANAGEMENT ===============
            1. Enroll Student in Course
            2. View Enrollments by Student
            3. View Enrollments by Course
            4. Update Enrollment Status
            5. View All Enrollments
            0. Back to Main Menu
            ===================================================
            """;
}
