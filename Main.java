import java.util.Scanner;
import java.util.InputMismatchException;

/**
 * The main entry point for the Student Learning Management System (SLMS) console application.
 *
 * <h2>Module Description</h2>
 * This class serves as the presentation layer of the SLMS. It provides an interactive,
 * text-based user interface using the {@link java.util.Scanner} class. The system is organized
 * into three core sub-modules — Course Management, Student Management, and Enrollment
 * Management — each accessible through a nested menu structure. Users can navigate through
 * these menus to perform CRUD (Create, Read, Update, Delete) operations on course and student
 * profiles, as well as manage the many-to-many relationships between them.
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Menu-driven architecture:</b> A hierarchical menu system was chosen over a
 *       command-line argument approach because it provides a guided user experience and
 *       reduces the likelihood of user errors.</li>
 *   <li><b>Input validation at the UI layer:</b> All user inputs are validated before being
 *       passed to the manager classes. This follows the defensive programming principle
 *       and ensures that the business logic layer receives only clean data.</li>
 *   <li><b>Scanner reuse:</b> A single {@link Scanner} instance is created and passed to all
 *       menu handlers to avoid resource leaks and ensure consistent input reading behavior.</li>
 *   <li><b>CacheAPI integration:</b> The {@link CacheAPI} is passed to each sub-menu to provide
 *       auto-suggestion functionality, demonstrating middleware API integration at the UI layer.</li>
 *   <li><b>Cache clearing on menu exit:</b> When the user navigates back from a sub-menu, the
 *       cache is cleared to prevent stale suggestions from appearing in unrelated contexts.</li>
 * </ul>
 *
 * <h2>Error Handling Strategy</h2>
 * All numeric menu selections are wrapped in try-catch blocks that handle
 * {@link InputMismatchException}. This prevents the application from crashing when a user
 * enters a non-numeric value where a number is expected. Invalid input is consumed from the
 * buffer and the user is prompted again.
 *
 * @see CourseManager
 * @see StudentManager
 * @see EnrollmentManager
 * @see CacheAPI
 */
public class Main {

    /**
     * The main method — entry point of the SLMS application.
     *
     * <p>Initializes all manager instances and the shared {@link Scanner}, then enters
     * the main menu loop. The loop continues until the user selects the exit option.</p>
     *
     * @param args Command-line arguments (not used in this application)
     */
    public static void main(String[] args) {
        // Initialize shared input scanner for the entire application
        Scanner scanner = new Scanner(System.in);

        // Initialize core manager modules — each manages its own data store
        CourseManager courseManager = new CourseManager();
        StudentManager studentManager = new StudentManager();

        // EnrollmentManager depends on both CourseManager and StudentManager for validation
        EnrollmentManager enrollmentManager = new EnrollmentManager(courseManager, studentManager);

        // CacheAPI acts as middleware — caches user inputs and provides auto-suggestions
        CacheAPI cacheAPI = new CacheAPI(courseManager, studentManager);

        // Main application loop control flag
        boolean running = true;

        System.out.println("Welcome to the Student Learning Management System (SLMS)");

        // MAIN MENU LOOP — continues until user selects exit
        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Course Management");
            System.out.println("2. Student Management");
            System.out.println("3. Enrollment Management");
            System.out.println("4. Exit");
            System.out.print("Select an option (1-4): ");

            int mainChoice;
            try {
                mainChoice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline character left by nextInt()
            } catch (InputMismatchException e) {
                // Handle non-numeric input gracefully without crashing
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input from buffer to prevent infinite loop
                continue;
            }

            // Route user selection to the appropriate sub-menu handler
            switch (mainChoice) {
                case 1:
                    handleCourseMenu(scanner, courseManager, cacheAPI);
                    break;
                case 2:
                    handleStudentMenu(scanner, studentManager, cacheAPI);
                    break;
                case 3:
                    handleEnrollmentMenu(scanner, enrollmentManager, courseManager, studentManager, cacheAPI);
                    break;
                case 4:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }

    /**
     * Handles the Course Management sub-menu and all associated user interactions.
     *
     * <p>Provides options to add, search, edit, delete, and view all courses.
     * Each operation interacts with the {@link CourseManager} for business logic
     * and the {@link CacheAPI} for input caching and auto-suggestions.</p>
     *
     * <h3>Input Validation Rules</h3>
     * <ul>
     *   <li>Course Code and Course Name cannot be empty</li>
     *   <li>Course Type must be one of: "core", "elective", or "university" (case-insensitive)</li>
     *   <li>Credit Hours must be a positive integer</li>
     * </ul>
     *
     * @param scanner  The shared Scanner instance for reading user input
     * @param manager  The CourseManager instance handling course business logic
     * @param cacheAPI The CacheAPI instance providing caching and auto-suggestion services
     */
    private static void handleCourseMenu(Scanner scanner, CourseManager manager, CacheAPI cacheAPI) {
        boolean courseMenuRunning = true;
        while (courseMenuRunning) {
            System.out.println("\n--- COURSE MANAGEMENT ---");
            System.out.println("1. Add Course");
            System.out.println("2. Search Course");
            System.out.println("3. Edit Course");
            System.out.println("4. Delete Course");
            System.out.println("5. View All Courses");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select an option (1-6): ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input from buffer
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n-- Add New Course --");

                    // Display auto-suggestions from existing courses and cached inputs
                    cacheAPI.showAutoSuggestion("course");

                    // --- Course Code Input ---
                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    if (code.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    // Cache the input for future auto-suggestion in this session
                    cacheAPI.cacheTextField("courseCode", code);

                    // --- Course Name Input ---
                    System.out.print("Enter Course Name: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Error: Course Name cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseName", name);

                    // --- Course Type Input with controlled vocabulary validation ---
                    System.out.print("Enter Course Type (core/elective/university): ");
                    String courseType = scanner.nextLine();
                    if (!courseType.equalsIgnoreCase("core") && !courseType.equalsIgnoreCase("elective") && !courseType.equalsIgnoreCase("university")) {
                        System.out.println("Error: Course Type must be 'core', 'elective', or 'university'.");
                        break;
                    }

                    // --- Credit Hours Input with numeric validation ---
                    System.out.print("Enter Credit Hours: ");
                    int credits = 0;
                    try {
                        credits = scanner.nextInt();
                        scanner.nextLine();
                    } catch (InputMismatchException e) {
                        System.out.println("Invalid input. Credit hours must be a positive integer.");
                        scanner.nextLine(); // Clear invalid input from buffer
                        break;
                    }
                    if (credits <= 0) {
                        System.out.println("Error: Credit hours must be a positive number.");
                        break;
                    }

                    // --- Optional fields — no validation required ---
                    System.out.print("Enter Course Summary: ");
                    String summary = scanner.nextLine();

                    System.out.print("Enter MS Teams Link: ");
                    String link = scanner.nextLine();

                    // Construct and store the new course object
                    Course newCourse = new Course(name, code, courseType, credits, summary, link);
                    manager.addCourse(newCourse);
                    break;

                case 2:
                    System.out.println("\n-- Search Course --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code to search: ");
                    String searchCode = scanner.nextLine();
                    if (searchCode.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", searchCode);

                    // searchCourse() returns null and prints "Course not found" if not found
                    Course foundCourse = manager.searchCourse(searchCode);
                    if (foundCourse != null) {
                        System.out.println("\n-- Search Result --");
                        foundCourse.displayCourse();
                    }
                    break;

                case 3:
                    System.out.println("\n-- Edit Course --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code to edit: ");
                    String editCode = scanner.nextLine();
                    if (editCode.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", editCode);

                    // First, locate the course to confirm it exists
                    Course courseToEdit = manager.searchCourse(editCode);

                    if (courseToEdit != null) {
                        System.out.println("Course found! Enter new details (Course Code cannot be changed).");

                        // --- Same validation rules as Add Course ---
                        System.out.print("Enter New Course Name: ");
                        String newName = scanner.nextLine();
                        if (newName.trim().isEmpty()) {
                            System.out.println("Error: Course Name cannot be empty.");
                            break;
                        }
                        System.out.print("Enter New Course Type (core/elective/university): ");
                        String newCourseType = scanner.nextLine();
                        if (!newCourseType.equalsIgnoreCase("core") && !newCourseType.equalsIgnoreCase("elective") && !newCourseType.equalsIgnoreCase("university")) {
                            System.out.println("Error: Course Type must be 'core', 'elective', or 'university'.");
                            break;
                        }
                        System.out.print("Enter New Credit Hours: ");
                        int newCredits = 0;
                        try {
                            newCredits = scanner.nextInt();
                            scanner.nextLine();
                        } catch (InputMismatchException e) {
                            System.out.println("Invalid input. Credit hours must be a positive integer.");
                            scanner.nextLine();
                            break;
                        }
                        if (newCredits <= 0) {
                            System.out.println("Error: Credit hours must be a positive number.");
                            break;
                        }
                        System.out.print("Enter New Course Summary: ");
                        String newSummary = scanner.nextLine();
                        System.out.print("Enter New MS Teams Link: ");
                        String newLink = scanner.nextLine();

                        // Delegate the update to CourseManager
                        manager.editCourse(editCode, newName, newCourseType, newCredits, newSummary, newLink);
                    }
                    break;

                case 4:
                    System.out.println("\n-- Delete Course --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code to delete: ");
                    String deleteCode = scanner.nextLine();
                    if (deleteCode.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", deleteCode);

                    // Locate the course first to display details before confirmation
                    Course courseToDelete = manager.searchCourse(deleteCode);

                    if (courseToDelete != null) {
                        courseToDelete.displayCourse();
                        // Confirmation prompt to prevent accidental deletion
                        System.out.print("Are you sure you want to delete this course? (Y/N): ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("Y")) {
                            manager.deleteCourse(deleteCode);
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n-- All Courses --");
                    manager.displayAllCourses();
                    break;

                case 6:
                    // Clear cached data before returning to prevent stale context
                    cacheAPI.clearCache();
                    courseMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Handles the Student Management sub-menu and all associated user interactions.
     *
     * <p>Provides options to add, search, edit, delete, and view all students.
     * Each operation interacts with the {@link StudentManager} for business logic
     * and the {@link CacheAPI} for input caching and auto-suggestions.</p>
     *
     * <h3>Input Validation Rules</h3>
     * <ul>
     *   <li>Student ID, First Name, Last Name, Email, and Phone Number cannot be empty</li>
     *   <li>Email must contain both '@' and '.' characters for basic format validation</li>
     *   <li>Student ID cannot be edited after creation (immutability enforced)</li>
     * </ul>
     *
     * @param scanner  The shared Scanner instance for reading user input
     * @param manager  The StudentManager instance handling student business logic
     * @param cacheAPI The CacheAPI instance providing caching and auto-suggestion services
     */
    private static void handleStudentMenu(Scanner scanner, StudentManager manager, CacheAPI cacheAPI) {
        boolean studentMenuRunning = true;
        while (studentMenuRunning) {
            System.out.println("\n--- STUDENT MANAGEMENT ---");
            System.out.println("1. Add Student");
            System.out.println("2. Search Student");
            System.out.println("3. Edit Student");
            System.out.println("4. Delete Student");
            System.out.println("5. View All Students");
            System.out.println("6. Back to Main Menu");
            System.out.print("Select an option (1-6): ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input from buffer
                continue;
            }

            switch (choice) {
                case 1:
                    System.out.println("\n-- Add New Student --");

                    // Display auto-suggestions from existing students and cached inputs
                    cacheAPI.showAutoSuggestion("student");

                    // --- Student ID Input ---
                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    if (id.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", id);

                    // --- First Name Input ---
                    System.out.print("Enter First Name: ");
                    String fName = scanner.nextLine();
                    if (fName.trim().isEmpty()) {
                        System.out.println("Error: First Name cannot be empty.");
                        break;
                    }

                    // --- Last Name Input ---
                    System.out.print("Enter Last Name: ");
                    String lName = scanner.nextLine();
                    if (lName.trim().isEmpty()) {
                        System.out.println("Error: Last Name cannot be empty.");
                        break;
                    }

                    // --- Email Input with basic format validation ---
                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    if (email.trim().isEmpty()) {
                        System.out.println("Error: Email cannot be empty.");
                        break;
                    }
                    // Basic email format check — must contain '@' and '.' characters
                    if (!email.contains("@") || !email.contains(".")) {
                        System.out.println("Error: Invalid email format. Email must contain '@' and '.'.");
                        break;
                    }

                    // --- Phone Number Input ---
                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    if (phone.trim().isEmpty()) {
                        System.out.println("Error: Phone Number cannot be empty.");
                        break;
                    }

                    // Construct and store the new student object
                    Student newStudent = new Student(fName, lName, id, email, phone);
                    manager.addStudent(newStudent);
                    break;

                case 2:
                    System.out.println("\n-- Search Student --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID to search: ");
                    String searchID = scanner.nextLine();
                    if (searchID.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", searchID);

                    // searchStudent() returns null and prints "Student not found" if not found
                    Student foundStudent = manager.searchStudent(searchID);
                    if (foundStudent != null) {
                        System.out.println("\n-- Search Result --");
                        foundStudent.displayStudent();
                    }
                    break;

                case 3:
                    System.out.println("\n-- Edit Student --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID to edit: ");
                    String editID = scanner.nextLine();
                    if (editID.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", editID);

                    // Locate the student to confirm existence before editing
                    Student studentToEdit = manager.searchStudent(editID);

                    if (studentToEdit != null) {
                        System.out.println("Student found! Enter new details (Student ID cannot be changed).");

                        // --- Same validation rules as Add Student ---
                        System.out.print("Enter New First Name: ");
                        String newFName = scanner.nextLine();
                        if (newFName.trim().isEmpty()) {
                            System.out.println("Error: First Name cannot be empty.");
                            break;
                        }
                        System.out.print("Enter New Last Name: ");
                        String newLName = scanner.nextLine();
                        if (newLName.trim().isEmpty()) {
                            System.out.println("Error: Last Name cannot be empty.");
                            break;
                        }
                        System.out.print("Enter New Email: ");
                        String newEmail = scanner.nextLine();
                        if (newEmail.trim().isEmpty()) {
                            System.out.println("Error: Email cannot be empty.");
                            break;
                        }
                        if (!newEmail.contains("@") || !newEmail.contains(".")) {
                            System.out.println("Error: Invalid email format. Email must contain '@' and '.'.");
                            break;
                        }
                        System.out.print("Enter New Phone Number: ");
                        String newPhone = scanner.nextLine();
                        if (newPhone.trim().isEmpty()) {
                            System.out.println("Error: Phone Number cannot be empty.");
                            break;
                        }

                        // Delegate the update to StudentManager
                        manager.editStudent(editID, newFName, newLName, newEmail, newPhone);
                    }
                    break;

                case 4:
                    System.out.println("\n-- Delete Student --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID to delete: ");
                    String deleteID = scanner.nextLine();
                    if (deleteID.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", deleteID);

                    // Display student details before asking for confirmation
                    Student studentToDelete = manager.searchStudent(deleteID);

                    if (studentToDelete != null) {
                        studentToDelete.displayStudent();
                        // Confirmation prompt to prevent accidental deletion
                        System.out.print("Are you sure you want to delete this student? (Y/N): ");
                        String confirm = scanner.nextLine();
                        if (confirm.equalsIgnoreCase("Y")) {
                            manager.deleteStudent(deleteID);
                        } else {
                            System.out.println("Deletion cancelled.");
                        }
                    }
                    break;

                case 5:
                    System.out.println("\n-- All Students --");
                    manager.displayAllStudents();
                    break;

                case 6:
                    // Clear cached data before returning to prevent stale context
                    cacheAPI.clearCache();
                    studentMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    /**
     * Handles the Enrollment Management sub-menu and all associated user interactions.
     *
     * <p>Provides options to establish course-student relationships, query relationships
     * from both perspectives (finding courses by student and students by course), list all
     * enrollments, and view the complete enrollment record.</p>
     *
     * <h3>Design Decisions</h3>
     * <ul>
     *   <li><b>Dual perspective operations:</b> "Add Course to Student" and "Add Student to Course"
     *       both create the same relationship but offer different entry points. This design
     *       accommodates different user workflows — some users think from the student's
     *       perspective, others from the course's perspective.</li>
     *   <li><b>Manager references passed explicitly:</b> Although EnrollmentManager already holds
     *       references to CourseManager and StudentManager, they are also passed here for
     *       potential future direct access needs at the UI level.</li>
     * </ul>
     *
     * @param scanner           The shared Scanner instance for reading user input
     * @param enrollmentManager The EnrollmentManager handling enrollment business logic
     * @param courseManager     The CourseManager for course data (passed for potential direct access)
     * @param studentManager    The StudentManager for student data (passed for potential direct access)
     * @param cacheAPI          The CacheAPI instance providing caching and auto-suggestion services
     */
    private static void handleEnrollmentMenu(Scanner scanner, EnrollmentManager enrollmentManager,
                                              CourseManager courseManager, StudentManager studentManager,
                                              CacheAPI cacheAPI) {
        boolean enrollmentMenuRunning = true;
        while (enrollmentMenuRunning) {
            System.out.println("\n--- ENROLLMENT MANAGEMENT ---");
            System.out.println("1. Add Course to Student");
            System.out.println("2. Add Student to Course");
            System.out.println("3. Find Course by Student ID");
            System.out.println("4. List All Courses for a Student");
            System.out.println("5. Find Student by Course Code");
            System.out.println("6. List All Students in a Course");
            System.out.println("7. View All Enrollments");
            System.out.println("8. Back to Main Menu");
            System.out.print("Select an option (1-8): ");

            int choice;
            try {
                choice = scanner.nextInt();
                scanner.nextLine();
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input from buffer
                continue;
            }

            switch (choice) {
                case 1:
                    // Student-perspective: start with student ID, then add course
                    System.out.println("\n-- Add Course to Student --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID: ");
                    String studentID1 = scanner.nextLine();
                    if (studentID1.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", studentID1);

                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code: ");
                    String courseCode1 = scanner.nextLine();
                    if (courseCode1.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", courseCode1);

                    enrollmentManager.addCourseToStudent(studentID1, courseCode1);
                    break;

                case 2:
                    // Course-perspective: start with course code, then add student
                    System.out.println("\n-- Add Student to Course --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code: ");
                    String courseCode2 = scanner.nextLine();
                    if (courseCode2.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", courseCode2);

                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID: ");
                    String studentID2 = scanner.nextLine();
                    if (studentID2.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", studentID2);

                    // Internally delegates to addCourseToStudent — same relationship, different entry point
                    enrollmentManager.addStudentToCourse(courseCode2, studentID2);
                    break;

                case 3:
                    // Find a single course associated with a student
                    System.out.println("\n-- Find Course by Student ID --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID: ");
                    String studentID3 = scanner.nextLine();
                    if (studentID3.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", studentID3);

                    enrollmentManager.findCourse(studentID3);
                    break;

                case 4:
                    // List all courses a student is enrolled in
                    System.out.println("\n-- List All Courses for a Student --");
                    cacheAPI.showAutoSuggestion("student");
                    System.out.print("Enter Student ID: ");
                    String studentID4 = scanner.nextLine();
                    if (studentID4.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", studentID4);

                    enrollmentManager.listCourses(studentID4);
                    break;

                case 5:
                    // Find a single student associated with a course
                    System.out.println("\n-- Find Student by Course Code --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code: ");
                    String courseCode5 = scanner.nextLine();
                    if (courseCode5.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", courseCode5);

                    enrollmentManager.findStudent(courseCode5);
                    break;

                case 6:
                    // List all students enrolled in a specific course
                    System.out.println("\n-- List All Students in a Course --");
                    cacheAPI.showAutoSuggestion("course");
                    System.out.print("Enter Course Code: ");
                    String courseCode6 = scanner.nextLine();
                    if (courseCode6.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseCode", courseCode6);

                    enrollmentManager.listStudents(courseCode6);
                    break;

                case 7:
                    // Display the complete enrollment record
                    System.out.println("\n-- All Enrollments --");
                    enrollmentManager.displayAllEnrollments();
                    break;

                case 8:
                    // Clear cached data before returning to prevent stale context
                    cacheAPI.clearCache();
                    enrollmentMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
