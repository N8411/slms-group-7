import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        CourseManager manager = new CourseManager();
        boolean running = true;

        System.out.println("Welcome to the Student Learning Management System (SLMS)");

        while (running) {
            System.out.println("\n--- MAIN MENU ---");
            System.out.println("1. Add Course (Submit)");
            System.out.println("2. Search Course");
            System.out.println("3. Edit Course");
            System.out.println("4. Delete Course");
            System.out.println("5. View All Courses");
            System.out.println("6. Exit");
            System.out.print("Select an option (1-6): ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume the newline character

            switch (choice) {
                case 1:
                    // Input fields for each course attribute 
                    System.out.println("\n-- Add New Course --");
                    System.out.print("Enter Course Name: ");
                    String name = scanner.nextLine();
                    System.out.print("Enter Course Code: ");
                    String code = scanner.nextLine();
                    System.out.print("Enter Credit Hours: ");
                    int credits = scanner.nextInt();
                    scanner.nextLine(); // Consume newline
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
                        foundCourse.displayCourse(); // Display complete course attributes [cite: 22]
                    }
                    break;
                case 3:
                    System.out.print("\nEnter Course Code to edit: ");
                    String editCode = scanner.nextLine();
                    Course courseToEdit = manager.searchCourse(editCode);
                    
                    if (courseToEdit != null) {
                        System.out.println("Course found! Enter new details (Course Code cannot be changed)."); // Editable except for course codes [cite: 22]
                        System.out.print("Enter New Course Name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter New Credit Hours: ");
                        int newCredits = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
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
                        System.out.print("Are you sure you want to delete this course? (Y/N): "); // Confirm message to confirm deletion [cite: 22]
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
                    manager.displayAllCourses(); // Displays all entered course attributes 
                    break;

                case 6:
                    running = false;
                    System.out.println("Exiting system. Goodbye!");
                    break;

                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }
    }
}
