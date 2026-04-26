import java.util.ArrayList;
import java.util.Arrays;

/**
 * Provides caching and auto-suggestion middleware services for the SLMS.
 *
 * <h2>Module Description</h2>
 * The CacheAPI class acts as a middleware layer between the user interface and the data
 * managers. It provides two primary services:
 * <ol>
 *   <li><b>Input caching:</b> Stores user inputs from text fields/forms during a session
 *       so that previously entered values can be referenced or suggested later.</li>
 *   <li><b>Auto-suggestion:</b> Combines cached inputs with existing system data to provide
 *       real-time suggestions to the user, improving input efficiency and reducing errors.</li>
 * </ol>
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>ArrayList for caching:</b> {@link ArrayList} was chosen over fixed-size arrays for
 *       the cache storage because the number of unique cached entries is unknown at startup
 *       and can grow dynamically. This uses the {@code java.util} API as required by the
 *       lab specifications.</li>
 *   <li><b>Separate cache lists by field type:</b> Course codes, student IDs, and course names
 *       are cached in separate lists. This allows type-specific retrieval and prevents
 *       cross-contamination of suggestions (e.g., a course code won't appear in a student
 *       ID suggestion list).</li>
 *   <li><b>Duplicate prevention:</b> The {@link ArrayList#contains(Object)} method is used to
 *       check for duplicates before adding to the cache. This keeps the cache clean and
 *       prevents redundant suggestions.</li>
 *   <li><b>Session-scoped cache:</b> The cache is cleared when the user navigates back to the
 *       main menu, simulating a session-scoped caching mechanism. This prevents stale
 *       suggestions from one context appearing in another.</li>
 *   <li><b>java.util API usage:</b> This class explicitly uses {@link ArrayList} and
 *       {@link Arrays} from the {@code java.util} package, as well as {@link String} methods
 *       from {@code java.lang}, satisfying the API usage requirements of the lab.</li>
 * </ul>
 *
 * <h2>API Documentation</h2>
 * <table border="1">
 *   <tr><th>Method</th><th>Purpose</th><th>Java API Used</th></tr>
 *   <tr><td>{@link #cacheTextField(String, String)}</td><td>Cache user input</td>
 *       <td>{@link ArrayList#contains(Object)}, {@link ArrayList#add(Object)}</td></tr>
 *   <tr><td>{@link #showAutoSuggestion(String)}</td><td>Display suggestions</td>
 *       <td>{@link Arrays#toString(Object[])}, {@link ArrayList#toString()}</td></tr>
 *   <tr><td>{@link #searchSuggestions(String, String)}</td><td>Search cached data</td>
 *       <td>{@link String#toLowerCase()}, {@link String#startsWith(String)},
 *           {@link ArrayList#toArray(Object[])}</td></tr>
 *   <tr><td>{@link #clearCache()}</td><td>Clear all cached data</td>
 *       <td>{@link ArrayList#clear()}</td></tr>
 * </table>
 *
 */
public class CacheAPI {

    /** Reference to CourseManager for retrieving existing course data. */
    private CourseManager courseManager;

    /** Reference to StudentManager for retrieving existing student data. */
    private StudentManager studentManager;

    /** Cached course codes entered by the user during the current session. */
    private ArrayList<String> cachedCourseCodes;

    /** Cached student IDs entered by the user during the current session. */
    private ArrayList<String> cachedStudentIDs;

    /** Cached course names entered by the user during the current session. */
    private ArrayList<String> cachedCourseNames;

    /** General-purpose cache for other text field inputs. */
    private ArrayList<String> cachedTextFieldInputs;

    /**
     * Constructs a new CacheAPI with references to the course and student managers.
     *
     * <p>Initializes all cache storage lists as empty ArrayLists.</p>
     *
     * @param cm The CourseManager instance for retrieving course data
     * @param sm The StudentManager instance for retrieving student data
     */
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
     *
     * <p>Simulates the API caching mechanism by storing user inputs in categorized lists.
     * Duplicate values are automatically filtered out using {@link ArrayList#contains(Object)}.</p>
     *
     * <h3>Supported Field Types</h3>
     * <ul>
     *   <li>{@code "courseCode"} — cached in the course codes list</li>
     *   <li>{@code "studentID"} — cached in the student IDs list</li>
     *   <li>{@code "courseName"} — cached in the course names list</li>
     *   <li>Any other value — cached in the general text field inputs list</li>
     * </ul>
     *
     * @param fieldType The type/category of the input field
     * @param value     The value entered by the user
     */
    public void cacheTextField(String fieldType, String value) {
        // Ignore null or empty values — no point caching them
        if (value == null || value.trim().isEmpty()) {
            return;
        }

        // Route to the appropriate cache list based on field type
        // Use ArrayList.contains() to prevent duplicate entries
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
                // Fallback cache for unrecognized field types
                if (!cachedTextFieldInputs.contains(value)) {
                    cachedTextFieldInputs.add(value);
                }
        }
    }

    /**
     * Displays auto-suggestions based on existing system data and cached inputs.
     *
     * <p>This method combines two sources of suggestion data:</p>
     * <ol>
     *   <li><b>Existing system data:</b> Retrieved from the CourseManager or StudentManager,
     *       representing courses or students that already exist in the system.</li>
     *   <li><b>Cached inputs:</b> Values previously entered by the user during the current
     *       session, providing quick access to recently used identifiers.</li>
     * </ol>
     *
     * <p>Uses {@link Arrays#toString(Object[])} to format the existing data arrays into
     * readable string output, and {@link ArrayList#toString()} for cached data.</p>
     *
     * @param type The suggestion type: {@code "course"} for course suggestions,
     *             {@code "student"} for student suggestions
     */
    public void showAutoSuggestion(String type) {
        switch (type) {
            case "course":
                // Display existing courses from the system data store
                String[] existingCourses = courseManager.getAllCourseDisplayStrings();
                if (existingCourses.length > 0) {
                    System.out.println("  [Auto-Suggest] Available courses: "
                            + Arrays.toString(existingCourses));
                }
                // Display recently entered course codes from cache
                if (!cachedCourseCodes.isEmpty()) {
                    System.out.println("  [Cache] Recently entered course codes: "
                            + cachedCourseCodes.toString());
                }
                break;

            case "student":
                // Display existing students from the system data store
                String[] existingStudents = studentManager.getAllStudentDisplayStrings();
                if (existingStudents.length > 0) {
                    System.out.println("  [Auto-Suggest] Available students: "
                            + Arrays.toString(existingStudents));
                }
                // Display recently entered student IDs from cache
                if (!cachedStudentIDs.isEmpty()) {
                    System.out.println("  [Cache] Recently entered student IDs: "
                            + cachedStudentIDs.toString());
                }
                break;
        }
    }

    /**
     * Searches cached data and existing system data for entries matching a given prefix.
     *
     * <p>This method simulates a search autocomplete feature. It performs case-insensitive
     * prefix matching using {@link String#toLowerCase()} and {@link String#startsWith(String)}.
     * Results from both the system data store and the cache are combined, with duplicates
     * removed.</p>
     *
     * @param type   The search type: {@code "course"} or {@code "student"}
     * @param prefix The partial input string to match against
     * @return An array of matching suggestion strings; empty array if no matches found
     */
    public String[] searchSuggestions(String type, String prefix) {
        // Convert prefix to lowercase for case-insensitive comparison
        String lowerPrefix = prefix.toLowerCase();
        ArrayList<String> matches = new ArrayList<>();

        if (type.equals("course")) {
            // Search existing courses for prefix matches
            String[] courses = courseManager.getAllCourseDisplayStrings();
            for (String course : courses) {
                if (course.toLowerCase().startsWith(lowerPrefix)) {
                    matches.add(course);
                }
            }
            // Also search cached course codes for prefix matches (avoiding duplicates)
            for (String cached : cachedCourseCodes) {
                if (cached.toLowerCase().startsWith(lowerPrefix)
                        && !matches.contains(cached)) {
                    matches.add(cached);
                }
            }
        } else if (type.equals("student")) {
            // Search existing students for prefix matches
            String[] students = studentManager.getAllStudentDisplayStrings();
            for (String student : students) {
                if (student.toLowerCase().startsWith(lowerPrefix)) {
                    matches.add(student);
                }
            }
            // Also search cached student IDs for prefix matches (avoiding duplicates)
            for (String cached : cachedStudentIDs) {
                if (cached.toLowerCase().startsWith(lowerPrefix)
                        && !matches.contains(cached)) {
                    matches.add(cached);
                }
            }
        }

        // Convert ArrayList to String array using toArray()
        return matches.toArray(new String[0]);
    }

    /**
     * Clears all cached data from every cache list.
     *
     * <p>This method is called when the user navigates back from a sub-menu to the main menu,
     * simulating a session-scoped cache that resets when the user changes context.</p>
     */
    public void clearCache() {
        cachedCourseCodes.clear();
        cachedStudentIDs.clear();
        cachedCourseNames.clear();
        cachedTextFieldInputs.clear();
        System.out.println("[CacheAPI] All cached data cleared.");
    }
}
