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

        // Use java.util API - ArrayList contains() and add() methods
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
    }

    /**
     * Clears all cached data.
     */
    public void clearCache() {

    }
}
