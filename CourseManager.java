/**
 * Manages a collection of {@link Course} objects for the SLMS.
 *
 * <h2>Module Description</h2>
 * This class handles all core CRUD (Create, Read, Update, Delete) operations for course
 * profiles. It utilizes a fixed-size array to store multiple course records and implements
 * boundary checks to prevent out-of-bounds errors. The manager acts as the business logic
 * layer between the presentation layer ({@link Main}) and the data model ({@link Course}).
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Fixed-size array:</b> A fixed-capacity array ({@code Course[50]}) was chosen over
 *       dynamic collections to demonstrate manual memory management and array boundary
 *       handling, as required by the lab specifications.</li>
 *   <li><b>Linear search:</b> A linear search algorithm is used for finding courses by code.
 *       This is appropriate for the small dataset size (max 50 courses). For larger datasets,
 *       a HashMap would be more efficient.</li>
 *   <li><b>Dual search methods:</b> Two search methods are provided — {@link #searchCourse(String)}
 *       for UI-facing searches (prints "Course not found") and {@link #getCourse(String)} for
 *       internal lookups (returns null silently). This prevents unwanted console output when
 *       other modules perform validation checks internally.</li>
 *   <li><b>Array shifting on delete:</b> When a course is deleted, subsequent elements are
 *       shifted left to maintain a contiguous array, preventing null gaps that would break
 *       search and display operations.</li>
 *   <li><b>Course code immutability:</b> The edit function does not allow modification of the
 *       course code, as it serves as the unique identifier and primary key for all lookups
 *       and enrollment relationships.</li>
 * </ul>
 *
 */
public class CourseManager {

    /** Fixed-size array to store course objects. Maximum capacity: 50. */
    private Course[] courseList = new Course[50];

    /** Tracks the current number of courses stored in the array. */
    private int courseCount = 0;

    /**
     * Adds a new course to the course list array.
     *
     * <p>Performs a boundary check to prevent array index out-of-bounds errors.
     * If the array is full, an error message is displayed and the operation is aborted.</p>
     *
     * @param newCourse The {@link Course} object to add to the list
     */
    public void addCourse(Course newCourse) {
        // Prevent array out-of-bound error by checking current count against capacity
        if (courseCount >= courseList.length) {
            System.out.println("Error: Course list is full!");
            return;
        }

        courseList[courseCount] = newCourse;
        courseCount++;
        System.out.println("Course added successfully!");
    }

    /**
     * Searches for a course by its course code using linear search.
     *
     * <p>This is the UI-facing search method that prints "Course not found" when
     * no match is found. For internal use by other managers, use {@link #getCourse(String)}
     * instead to avoid unwanted console output.</p>
     *
     * @param searchCode The course code to search for
     * @return The matching {@link Course} object, or {@code null} if not found
     */
    public Course searchCourse(String searchCode) {
        // Linear search: iterate through all stored courses
        for (int i = 0; i < courseCount; i++) {
            // Use .equals() for String comparison, not == (reference comparison)
            if (courseList[i].getCourseCode().equals(searchCode)) {
                return courseList[i]; // Match found — return the course object
            }
        }

        // No match found — inform the user
        System.out.println("Course not found");
        return null;
    }

    /**
     * Silently retrieves a course by its code without printing any messages.
     *
     * <p>This method is intended for internal use by other manager classes
     * (e.g., {@link EnrollmentManager}) that need to validate course existence
     * without producing console output.</p>
     *
     * @param searchCode The course code to look up
     * @return The matching {@link Course} object, or {@code null} if not found
     */
    public Course getCourse(String searchCode) {
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equals(searchCode)) {
                return courseList[i];
            }
        }
        return null; // Silent return — no console output
    }

    /**
     * Edits an existing course's profile information.
     *
     * <p>Locates the course using the provided code, then updates all mutable attributes
     * (name, type, credit hours, summary, MS Teams link). The course code itself cannot
     * be changed, as it serves as the unique identifier and is referenced by enrollment
     * records.</p>
     *
     * <p>If the course is not found, the method relies on {@link #searchCourse(String)}
     * to display the appropriate error message.</p>
     *
     * @param searchCode    The course code of the course to edit
     * @param newName       The new course name to set
     * @param newCourseType The new course type to set ("core", "elective", or "university")
     * @param newCredits    The new credit hours value to set
     * @param newSummary    The new course summary to set
     * @param newLink       The new MS Teams link to set
     */
    public void editCourse(String searchCode, String newName, String newCourseType, int newCredits, String newSummary, String newLink) {
        // Use the UI-facing search to locate the course (prints "not found" if absent)
        Course targetCourse = searchCourse(searchCode);

        if (targetCourse != null) {
            // Update all attributes except Course Code (immutable)
            targetCourse.setCourseName(newName);
            targetCourse.setCourseType(newCourseType);
            targetCourse.setCreditHours(newCredits);
            targetCourse.setSummary(newSummary);
            targetCourse.setMsTeamsLink(newLink);

            // Display the updated profile to validate changes
            System.out.println("Course updated! Here are the validated changes:");
            targetCourse.displayCourse();
        }
    }

    /**
     * Deletes a course from the list by its course code.
     *
     * <p>The deletion process involves three steps:</p>
     * <ol>
     *   <li>Locate the target course's index in the array</li>
     *   <li>Shift all subsequent elements left to fill the gap</li>
     *   <li>Set the last occupied position to null and decrement the count</li>
     * </ol>
     *
     * <p>After deletion, all remaining courses are displayed to validate the operation.</p>
     *
     * @param searchCode The course code of the course to delete
     */
    public void deleteCourse(String searchCode) {
        int targetIndex = -1;

        // Step 1: Find the index of the target course
        for (int i = 0; i < courseCount; i++) {
            if (courseList[i].getCourseCode().equals(searchCode)) {
                targetIndex = i;
                break; // Stop searching once found
            }
        }

        if (targetIndex != -1) {
            // Course found — proceed with deletion
            System.out.println("Course " + searchCode + " deleted.");

            // Step 2: Shift elements left to fill the gap left by the deleted course
            for (int i = targetIndex; i < courseCount - 1; i++) {
                courseList[i] = courseList[i + 1];
            }

            // Step 3: Clear the last occupied position and decrement count
            courseList[courseCount - 1] = null;
            courseCount--;

            // Validate deletion by showing remaining courses
            displayAllCourses();
        } else {
            System.out.println("Course not found");
        }
    }

    /**
     * Displays all courses currently stored in the system.
     *
     * <p>If no courses are available, a message is displayed indicating the empty state.
     * Otherwise, each course's information is displayed using the {@link Course#displayCourse()}
     * method.</p>
     */
    public void displayAllCourses() {
        System.out.println("=== All Enrolled Courses ===");
        if (courseCount == 0) {
            System.out.println("No courses available.");
            return;
        }

        // Iterate through all stored courses and display their information
        for (int i = 0; i < courseCount; i++) {
            courseList[i].displayCourse();
        }
    }

    // ==================== API Support Methods ====================
    // These methods support the CacheAPI auto-suggestion feature.

    /**
     * Returns display-formatted strings for all courses.
     *
     * <p>Each string follows the format: {@code "courseCode - courseName"}.
     * Used by {@link CacheAPI} to generate auto-suggestion lists.</p>
     *
     * @return An array of formatted course display strings
     */
    public String[] getAllCourseDisplayStrings() {
        String[] displays = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            displays[i] = courseList[i].getCourseCode() + " - " + courseList[i].getCourseName();
        }
        return displays;
    }

    /**
     * Returns all course codes currently stored in the system.
     *
     * <p>Used by {@link CacheAPI} for auto-suggestion and search autocomplete features.</p>
     *
     * @return An array of all course codes
     */
    public String[] getAllCourseCodes() {
        String[] codes = new String[courseCount];
        for (int i = 0; i < courseCount; i++) {
            codes[i] = courseList[i].getCourseCode();
        }
        return codes;
    }
}
