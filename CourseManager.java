public class CourseManager {
    private Course[] courseList = new Course[50]; // Fixed array for storage
    private int courseCount = 0;

    // 1. Course Input Implementation
    public void addCourse(Course newCourse) {
        // Prevent array out-of-bound error
        if (courseCount >= courseList.length) {
            System.out.println("Error: Course list is full!");
            return;
        }
        
        courseList[courseCount] = newCourse;
        courseCount++;
        System.out.println("Course added successfully!");
    }

    // 2. Search Function Development
    public Course searchCourse(String searchCode) {
        // Implement a linear search function
        for (int i = 0; i < courseCount; i++) {
            // Use .equals() for string comparison in Java
            if (courseList[i].getCourseCode().equals(searchCode)) {
                return courseList[i]; // Return successful search
            }
        }
        
        System.out.println("Course not found"); 
        return null;
    }
    
 // 3. Edit Function Development
    public void editCourse(String searchCode, String newName, int newCredits, String newSummary, String newLink) {
        Course targetCourse = searchCourse(searchCode);
        
        if (targetCourse != null) {
            // Update all attributes except Course Code
            targetCourse.setCourseName(newName);
            targetCourse.setCreditHours(newCredits);
            targetCourse.setSummary(newSummary);
            targetCourse.setMsTeamsLink(newLink);
            
            System.out.println("Course updated! Here are the validated changes:");
            targetCourse.displayCourse();
        }
    }

    // 4. Delete Function Development
    public void deleteCourse(String searchCode) {
        int targetIndex = -1;

        // Search for the target course to delete
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equals(searchCode)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1) {
            // Confirm message simulation 
            System.out.println("Course " + searchCode + " deleted.");
            
            // Shift elements to fill the gap
            for (int i = targetIndex; i < courseCount - 1; i++) {
                courseList[i] = courseList[i + 1];
            }
            
            courseList[courseCount - 1] = null;
            courseCount--;
            
            // Validate deletion
            displayAllCourses();
        } else {
            System.out.println("Course not found");
        }
    }

    // 5. Data Display Function Development
    public void displayAllCourses() {
        System.out.println("=== All Enrolled Courses ===");
        if (courseCount == 0) {
            System.out.println("No courses available.");
            return;
        }

        // Display all course attributes in an organized manner
        for (int i = 0; i < courseCount; i++) {
            courseList[i].displayCourse();
        }
    }
}
