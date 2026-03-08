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
}
