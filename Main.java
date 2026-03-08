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
                    /* implement case 3 - 6
            }
        }
        /*
        scanner.close();
        8. For Edit Course:
           - Ask for Course Code.
           - If course exists, allow editing all attributes except Course Code.
           - Call manager.editCourse().

        9. For Delete Course:
           - Ask for Course Code.
           - Display course details before deleting.
           - Ask user for confirmation (Y/N).
           - If confirmed, call manager.deleteCourse().

        10. For View All Courses:
            - Call manager.displayAllCourses().

        11. Exit the program when the user selects option 6.

        12. Close the Scanner before the program ends.
        */
    }
}
