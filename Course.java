/**
 * Represents a course profile within the Student Learning Management System (SLMS).
 *
 * <h2>Module Description</h2>
 * This class serves as the data model (entity) for a course. It encapsulates all course
 * attributes including the course name, unique course code, course type, credit hours,
 * summary, and MS Teams link. It provides getter and setter methods for data manipulation,
 * following the JavaBeans convention.
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Course code immutability:</b> The course code is set only through the constructor
 *       and has no setter method. This enforces the business rule that a course's code
 *       cannot be changed after creation, as it serves as the primary key for all lookups.</li>
 *   <li><b>Course type as controlled vocabulary:</b> The course type field accepts only
 *       "core", "elective", or "university" values. Validation is performed at the UI layer
 *       ({@link Main}) before the object is created, ensuring data consistency.</li>
 *   <li><b>Self-display method:</b> The {@link #displayCourse()} method is included in the
 *       model class following the principle of information expert — the class that owns
 *       the data knows best how to present it.</li>
 *   <li><b>Private data members:</b> All fields are private to enforce encapsulation.
 *       External access is only possible through the provided getter and setter methods.</li>
 * </ul>
 *
 */
public class Course {

    /** The full name of the course. */
    private String courseName;

    /** The unique course code — immutable after construction. */
    private String courseCode;

    /** The course type: must be "core", "elective", or "university". */
    private String courseType;

    /** The number of credit hours assigned to the course. */
    private int creditHours;

    /** A brief description/summary of the course content. */
    private String summary;

    /** The Microsoft Teams meeting link for the course. */
    private String msTeamsLink;

    /**
     * Constructs a new Course with the specified details.
     *
     * <p>All attributes are initialized upon creation. The course code is set here
     * and cannot be modified afterwards.</p>
     *
     * @param name        The course name
     * @param code        The unique course code (immutable)
     * @param courseType  The course type ("core", "elective", or "university")
     * @param creditHours The number of credit hours
     * @param summary     A brief course summary
     * @param msTeamsLink The MS Teams link for the course
     */
    public Course(String name, String code, String courseType, int creditHours, String summary, String msTeamsLink) {
        this.courseName = name;
        this.courseCode = code;
        this.courseType = courseType;
        this.creditHours = creditHours;
        this.summary = summary;
        this.msTeamsLink = msTeamsLink;
    }

    // ==================== Getter Methods ====================

    /**
     * Returns the course name.
     *
     * @return the name of the course
     */
    public String getCourseName() {
        return courseName;
    }

    /**
     * Returns the unique course code.
     *
     * <p>Note: There is no setter for this field to enforce immutability.</p>
     *
     * @return the course code
     */
    public String getCourseCode() {
        return courseCode;
    }

    /**
     * Returns the course type.
     *
     * @return the course type ("core", "elective", or "university")
     */
    public String getCourseType() {
        return courseType;
    }

    /**
     * Returns the number of credit hours.
     *
     * @return the credit hours for this course
     */
    public int getCreditHours() {
        return creditHours;
    }

    /**
     * Returns the course summary.
     *
     * @return the summary description of the course
     */
    public String getSummary() {
        return summary;
    }

    /**
     * Returns the MS Teams link.
     *
     * @return the Microsoft Teams meeting link for the course
     */
    public String getMsTeamsLink() {
        return msTeamsLink;
    }

    // ==================== Setter Methods ====================
    // Note: No setter for courseCode — it is immutable after construction.

    /**
     * Sets the course name.
     *
     * @param courseName the new course name to set
     */
    public void setCourseName(String courseName) {
        this.courseName = courseName;
    }

    /**
     * Sets the course type.
     *
     * @param courseType the new course type to set ("core", "elective", or "university")
     */
    public void setCourseType(String courseType) {
        this.courseType = courseType;
    }

    /**
     * Sets the number of credit hours.
     *
     * @param creditHours the new credit hours value to set
     */
    public void setCreditHours(int creditHours) {
        this.creditHours = creditHours;
    }

    /**
     * Sets the course summary.
     *
     * @param summary the new course summary to set
     */
    public void setSummary(String summary) {
        this.summary = summary;
    }

    /**
     * Sets the MS Teams link.
     *
     * @param msTeamsLink the new MS Teams link to set
     */
    public void setMsTeamsLink(String msTeamsLink) {
        this.msTeamsLink = msTeamsLink;
    }

    // ==================== Display Method ====================

    /**
     * Displays the course information in a formatted console output.
     *
     * <p>The output includes the course code, name, type, and credit hours on the first line,
     * followed by the summary and Teams link on subsequent lines. A blank line is appended
     * for visual separation between multiple course displays.</p>
     */
    public void displayCourse() {
        System.out.println("Code: " + courseCode + " | Name: " + courseName + " | Type: " + courseType + " | Credits: " + creditHours);
        System.out.println("Summary: " + summary);
        System.out.println("Teams Link: " + msTeamsLink + "\n");
    }
}
