import java.util.ArrayList;
import java.util.Arrays;

public class CacheAPI {
    // References to managers for data retrieval
    private CourseManager courseManager;
    private StudentManager studentManager;

    // Cached input history (simulates caching textfield/form data)
    private ArrayList<String> cachedCourseCodes;
    private ArrayList<String> cachedStudentIDs;
    private ArrayList<String> cachedCourseNames;
    private ArrayList<String> cachedTextFieldInputs;

    // Constructor
    public CacheAPI(CourseManager cm, StudentManager sm) {
        this.courseManager = cm;
        this.studentManager = sm;
        this.cachedCourseCodes = new ArrayList<>();
        this.cachedStudentIDs = new ArrayList<>();
        this.cachedCourseNames = new ArrayList<>();
        this.cachedTextFieldInputs = new ArrayList<>();
    }

    /**
     * Caches input from text fields or forms for future reference.
     * Simulates the API caching mechanism.
     *
     * @param fieldType The type of field (e.g., "courseCode", "studentID", "courseName")
     * @param value     The value entered by the user
     */
    public void cacheTextField(String fieldType, String value) {
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        // Use java.util API - ArrayList contains() and add() methods
        switch (fieldType) {
            case "courseCode":
                if (!cachedCourseCodes.contains(value)) {
                    cachedCourseCodes.add(value);
                }
                break;
            case "studentID":
                if (!cachedStudentIDs.contains(value)) {
                    cachedStudentIDs.add(value);
                }
                break;
            case "courseName":
                if (!cachedCourseNames.contains(value)) {
                    cachedCourseNames.add(value);
                }
                break;
            default:
                if (!cachedTextFieldInputs.contains(value)) {
                    cachedTextFieldInputs.add(value);
                }
        }
    }

    /**
     * Shows auto-suggestions based on cached data and existing system data.
     * Implements Auto suggestion of input based on cached data.
     *
     * Uses java.util.Arrays API to convert and manipulate data arrays.
     *
     * @param type "course" or "student" to determine which suggestions to show
     */
    public void showAutoSuggestion(String type) {
        // Use java.util.Arrays API (as mentioned by lecturer - library like Math is also acceptable)
        switch (type) {
            case "course":
                String[] existingCourses = courseManager.getAllCourseDisplayStrings();
                if (existingCourses.length > 0) {
                    System.out.println("  [Auto-Suggest] Available courses: "
                            + Arrays.toString(existingCourses));
                }
                if (!cachedCourseCodes.isEmpty()) {
                    System.out.println("  [Cache] Recently entered course codes: "
                            + cachedCourseCodes.toString());
                }
                break;

            case "student":
                String[] existingStudents = studentManager.getAllStudentDisplayStrings();
                if (existingStudents.length > 0) {
                    System.out.println("  [Auto-Suggest] Available students: "
                            + Arrays.toString(existingStudents));
                }
                if (!cachedStudentIDs.isEmpty()) {
                    System.out.println("  [Cache] Recently entered student IDs: "
                            + cachedStudentIDs.toString());
                }
                break;
        }
    }

    /**
     * Searches cached data for a partial match (simulates search autocomplete).
     * Uses java.lang.String API methods (startsWith, toLowerCase).
     *
     * @param type   "course" or "student"
     * @param prefix The partial input to match against
     * @return Array of matching suggestions
     */
    public String[] searchSuggestions(String type, String prefix) {
        // Use java.lang.String API - toLowerCase() and startsWith()
        String lowerPrefix = prefix.toLowerCase();
        ArrayList<String> matches = new ArrayList<>();

        if (type.equals("course")) {
            String[] courses = courseManager.getAllCourseDisplayStrings();
            for (String course : courses) {
                if (course.toLowerCase().startsWith(lowerPrefix)) {
                    matches.add(course);
                }
            }
            // Also check cached entries
            for (String cached : cachedCourseCodes) {
                if (cached.toLowerCase().startsWith(lowerPrefix)
                        && !matches.contains(cached)) {
                    matches.add(cached);
                }
            }
        } else if (type.equals("student")) {
            String[] students = studentManager.getAllStudentDisplayStrings();
            for (String student : students) {
                if (student.toLowerCase().startsWith(lowerPrefix)) {
                    matches.add(student);
                }
            }
            for (String cached : cachedStudentIDs) {
                if (cached.toLowerCase().startsWith(lowerPrefix)
                        && !matches.contains(cached)) {
                    matches.add(cached);
                }
            }
        }

        // Use java.util.ArrayList API - toArray()
        return matches.toArray(new String[0]);
    }
    
    /**
     * Clears all cached data.
     */
    public void clearCache() {
        cachedCourseCodes.clear();
        cachedStudentIDs.clear();
        cachedCourseNames.clear();
        cachedTextFieldInputs.clear();
        System.out.println("[CacheAPI] All cached data cleared.");
    }
}
