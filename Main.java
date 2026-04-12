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
            
            int mainChoice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline

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
    private static void handleCourseMenu(Scanner scanner, CourseManager manager) {
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
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.println("\n-- Add New Course --");
                    System.out.print("Enter Course Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Credit Hours: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine(); 
                    System.out.print("Enter Course Summary: ");
                    String summary = scanner.nextLine();
                    System.out.print("Enter MS Teams Link: ");
                    String link = scanner.nextLine();

                    Course newCourse = new Course(name, code, credits, summary, link);
                    manager.addCourse(newCourse);
                    break;
                case 2:
                    System.out.print("\nEnter Course Code to search: ");
                    String searchCode = scanner.nextLine();
                    Course foundCourse = manager.searchCourse(searchCode);
                    if (foundCourse != null) {
                        System.out.println("\n-- Search Result --");
                        foundCourse.displayCourse();
                    }
                    break;
                case 3:
                    System.out.print("\nEnter Course Code to edit: ");
                    String editCode = scanner.nextLine();
                    Course courseToEdit = manager.searchCourse(editCode);
                    
                    if (courseToEdit != null) {
                        System.out.println("Course found! Enter new details (Course Code cannot be changed).");
                        System.out.print("Enter New Course Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter New Credit Hours: ");
                        int newCredits = scanner.nextInt();
                        scanner.nextLine();
                        System.out.print("Enter New Course Summary: ");
                        String newSummary = scanner.nextLine();
                        System.out.print("Enter New MS Teams Link: ");
                        String newLink = scanner.nextLine();

                        manager.editCourse(editCode, newName, newCredits, newSummary, newLink);
                    }
                    break;
                case 4:
                    System.out.print("\nEnter Course Code to delete: ");
                    String deleteCode = scanner.nextLine();
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
                    courseMenuRunning = false;
                    break;
                default:
                    System.out.println("Invalid option.");
            }
        }
    }

    // STUDENT MANAGEMENT MENU

