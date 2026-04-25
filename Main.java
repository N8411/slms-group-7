/**
 * The main entry point for the Student Learning Management System (SLMS) console application.
 * 
 * This class provides an interactive, text-based user interface using the {@link java.util.Scanner} 
 * class. It integrates the {@link CourseManager} and {@link StudentManager} modules into a single, 
 * cohesive system. Users can navigate through nested menus to perform various management operations 
 * on both course and student profiles safely and continuously until they choose to exit.
 * 
 *
 */
import java.util.Scanner;
import java.util.InputMismatchException;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManager courseManager = new CourseManager();
        StudentManager studentManager = new StudentManager();
        EnrollmentManager enrollmentManager = new EnrollmentManager(courseManager, studentManager);
        CacheAPI cacheAPI = new CacheAPI(courseManager, studentManager);
        boolean running = true;

        System.out.println("Welcome to the Student Learning Management System (SLMS)");

        // MAIN MENU
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
                scanner.nextLine(); // Consume the newline
            } catch (InputMismatchException e) {
                System.out.println("Invalid input. Please enter a valid number.");
                scanner.nextLine(); // Clear invalid input from buffer
                continue;
            }

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

    // COURSE MANAGEMENT MENU
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

                    // Auto-suggestion for Course Code
                    cacheAPI.showAutoSuggestion("course");

                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    if (code.trim().isEmpty()) {
                        System.out.println("Error: Course Code cannot be empty.");
                        break;
                    }
                    // Cache the input for future auto-suggestion
                    cacheAPI.cacheTextField("courseCode", code);

                    System.out.print("Enter Course Name: ");
                    String name = scanner.nextLine();
                    if (name.trim().isEmpty()) {
                        System.out.println("Error: Course Name cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("courseName", name);

                    System.out.print("Enter Course Type (core/elective/university): ");
                    String courseType = scanner.nextLine();
                    if (!courseType.equalsIgnoreCase("core") && !courseType.equalsIgnoreCase("elective") && !courseType.equalsIgnoreCase("university")) {
                        System.out.println("Error: Course Type must be 'core', 'elective', or 'university'.");
                        break;
                    }

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

                    System.out.print("Enter Course Summary: ");
                    String summary = scanner.nextLine();

                    System.out.print("Enter MS Teams Link: ");
                    String link = scanner.nextLine();

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

                    Course courseToEdit = manager.searchCourse(editCode);

                    if (courseToEdit != null) {
                        System.out.println("Course found! Enter new details (Course Code cannot be changed).");
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
                            scanner.nextLine(); // Clear invalid input from buffer
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

                    Course courseToDelete = manager.searchCourse(deleteCode);

                    if (courseToDelete != null) {
                        courseToDelete.displayCourse();
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
                    cacheAPI.clearCache();
                    courseMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // STUDENT MANAGEMENT MENU
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

                    // Auto-suggestion for Student ID
                    cacheAPI.showAutoSuggestion("student");

                    System.out.print("Enter Student ID: ");
                    String id = scanner.nextLine();
                    if (id.trim().isEmpty()) {
                        System.out.println("Error: Student ID cannot be empty.");
                        break;
                    }
                    cacheAPI.cacheTextField("studentID", id);

                    System.out.print("Enter First Name: ");
                    String fName = scanner.nextLine();
                    if (fName.trim().isEmpty()) {
                        System.out.println("Error: First Name cannot be empty.");
                        break;
                    }

                    System.out.print("Enter Last Name: ");
                    String lName = scanner.nextLine();
                    if (lName.trim().isEmpty()) {
                        System.out.println("Error: Last Name cannot be empty.");
                        break;
                    }

                    System.out.print("Enter Email: ");
                    String email = scanner.nextLine();
                    if (email.trim().isEmpty()) {
                        System.out.println("Error: Email cannot be empty.");
                        break;
                    }
                    if (!email.contains("@") || !email.contains(".")) {
                        System.out.println("Error: Invalid email format. Email must contain '@' and '.'.");
                        break;
                    }

                    System.out.print("Enter Phone Number: ");
                    String phone = scanner.nextLine();
                    if (phone.trim().isEmpty()) {
                        System.out.println("Error: Phone Number cannot be empty.");
                        break;
                    }

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

                    Student studentToEdit = manager.searchStudent(editID);

                    if (studentToEdit != null) {
                        System.out.println("Student found! Enter new details (Student ID cannot be changed).");
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

                    Student studentToDelete = manager.searchStudent(deleteID);

                    if (studentToDelete != null) {
                        studentToDelete.displayStudent();
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
                    cacheAPI.clearCache();
                    studentMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }

   // Enrollment Management Menu
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

                    enrollmentManager.addStudentToCourse(courseCode2, studentID2);
                    break;

                case 3:
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
                    System.out.println("\n-- All Enrollments --");
                    enrollmentManager.displayAllEnrollments();
                    break;

                case 8:
                    cacheAPI.clearCache();
                    enrollmentMenuRunning = false;
                    break;

                default:
                    System.out.println("Invalid option.");
            }
        }
    }
}
