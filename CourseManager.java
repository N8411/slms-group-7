/**
 * Manages a collection of {@link Course} objects for the SLMS.
 * 
 * This class handles the core business logic for course profiles using a fixed-size array. 
 * It includes boundary checks to prevent storage errors and implements a linear search 
 * algorithm to locate courses by their unique course code. The manager allows users to 
 * add new courses, edit existing course details (excluding the course code), safely 
 * delete courses by shifting array elements, and display all active courses.
 * 
 */
public class CourseManager {
    // private data members
    private Course[] courseList = new Course[50]; // Fixed array for storage
    private int courseCount = 0;

    // 1. Course Input Implementation
    public void addCourse(Course newCourse) {
        // Check for null course
        if (newCourse == null) {
            System.out.println("Error: Course data cannot be null!");
            return;
        }

        // Check for duplicate course code
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equals(newCourse.getCourseCode())) {
                System.out.println("Error: Course with code '" + newCourse.getCourseCode() + "' already exists!");
                return;
            }
        }

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
        // Validate input parameter
        if (searchCode == null || searchCode.trim().isEmpty()) {
            System.out.println("Error: Search code cannot be empty.");
            return null;
        }

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

    // Silent search (for internal use by EnrollmentManager - no print)
    public Course getCourse(String searchCode) {
        // Validate input parameter
        if (searchCode == null || searchCode.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equals(searchCode)) {
                return courseList[i];
            }
        }
        return null;
    }
    
    // 3. Edit Function Development (updated with courseType)
    public void editCourse(String searchCode, String newName, String newCourseType, int newCredits, String newSummary, String newLink) {
        Course targetCourse = searchCourse(searchCode);

        if (targetCourse != null) {
            targetCourse.setCourseName(newName);
            targetCourse.setCourseType(newCourseType);
            targetCourse.setCreditHours(newCredits);
            targetCourse.setSummary(newSummary);
            targetCourse.setMsTeamsLink(newLink);

            System.out.println("Course updated! Here are the validated changes:");
            targetCourse.displayCourse();
        }
    }

    // 4. Delete Function Development
    public void deleteCourse(String searchCode) {
        // Validate input parameter
        if (searchCode == null || searchCode.trim().isEmpty()) {
            System.out.println("Error: Course code cannot be empty.");
            return;
        }

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

    // Get all course display strings for API auto-suggestion
    public String[] getAllCourseDisplayStrings() {
        String[] displays = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            displays[i] = courseList[i].getCourseCode() + " - " + courseList[i].getCourseName();
        }
        return displays;
    }

    // Get all course codes
    public String[] getAllCourseCodes() {
        String[] codes = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            codes[i] = courseList[i].getCourseCode();
        }
        return codes;
    }
}
