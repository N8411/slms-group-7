/**
 * Manages Course-Student relationships for the SLMS using parallel arrays.
 *
 * <h2>Module Description</h2>
 * This class implements the core relationship management functions required for the SLMS.
 * It uses two parallel arrays ({@link #enrollmentStudentIDs} and {@link #enrollmentCourseCodes})
 * where each index {@code i} represents one enrollment record: the student with ID
 * {@code enrollmentStudentIDs[i]} is enrolled in the course with code
 * {@code enrollmentCourseCodes[i]}.
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Parallel arrays over 2D array:</b> Two separate 1D arrays were chosen over a single
 *       2D array because they provide clearer semantics — one array for student IDs and one for
 *       course codes. This makes the code more readable and easier to maintain. Additionally,
 *       parallel arrays allow for type safety (both are String arrays), whereas a 2D array
 *       would require Object[][] and subsequent casting.</li>
 *   <li><b>Manager references for validation:</b> The EnrollmentManager holds references to
 *       both {@link CourseManager} and {@link StudentManager}. This allows it to validate that
 *       students and courses exist before creating relationships, following the referential
 *       integrity principle from database design.</li>
 *   <li><b>Dual entry points:</b> Both {@link #addCourseToStudent(String, String)} and
 *       {@link #addStudentToCourse(String, String)} are provided. They create the same
 *       relationship but accommodate different user workflows — thinking from the student's
 *       perspective or the course's perspective.</li>
 *   <li><b>Duplicate enrollment prevention:</b> The {@link #isEnrolled(String, String)} helper
 *       method checks for existing relationships before creating new ones, preventing
 *       duplicate enrollment records.</li>
 *   <li><b>Comprehensive error handling:</b> All public methods validate their inputs and
 *       provide clear, descriptive error messages for: student/course not found, duplicate
 *       enrollment, student without course, and course without student scenarios.</li>
 * </ul>
 *
 * <h2>Data Structure Visualization</h2>
 * <pre>
 * Index:        0         1         2         3
 * StudentIDs: ["S001"]  ["S002"]  ["S001"]  ["S003"]
 * CourseCodes: ["CSEB5223"]["CSEB5223"]["CSEB5244"]["CSEB5223"]
 *
 * Meaning: S001 is enrolled in CSEB5223 and CSEB5244
 *          S002 is enrolled in CSEB5223
 *          S003 is enrolled in CSEB5223
 * </pre>
 *
 */
public class EnrollmentManager {

    /** Parallel array storing student IDs for each enrollment record. */
    private String[] enrollmentStudentIDs;

    /** Parallel array storing course codes for each enrollment record. */
    private String[] enrollmentCourseCodes;

    /** Tracks the current number of enrollment records stored. */
    private int enrollmentCount;

    /** Maximum number of enrollment records that can be stored. */
    private static final int MAX_ENROLLMENTS = 500;

    /** Reference to CourseManager for course existence validation. */
    private CourseManager courseManager;

    /** Reference to StudentManager for student existence validation. */
    private StudentManager studentManager;

    /**
     * Constructs a new EnrollmentManager with references to the course and student managers.
     *
     * <p>Initializes empty parallel arrays with the maximum enrollment capacity.
     * The manager references are stored for referential integrity validation.</p>
     *
     * @param cm The CourseManager instance for validating course existence
     * @param sm The StudentManager instance for validating student existence
     */
    public EnrollmentManager(CourseManager cm, StudentManager sm) {
        enrollmentStudentIDs = new String[MAX_ENROLLMENTS];
        enrollmentCourseCodes = new String[MAX_ENROLLMENTS];
        enrollmentCount = 0;
        this.courseManager = cm;
        this.studentManager = sm;
    }

    /**
     * Adds a course to a student, creating a new enrollment relationship.
     *
     * <p>This method performs the following validations before creating the relationship:</p>
     * <ol>
     *   <li>Verifies the student exists in the StudentManager</li>
     *   <li>Verifies the course exists in the CourseManager</li>
     *   <li>Checks that the student is not already enrolled in this course</li>
     *   <li>Verifies the enrollment array has available capacity</li>
     * </ol>
     *
     * @param studentID  The ID of the student to enroll
     * @param courseCode The code of the course to enroll the student in
     * @return {@code true} if the enrollment was created successfully;
     *         {@code false} if validation failed or the array is full
     */
    public boolean addCourseToStudent(String studentID, String courseCode) {
        // Validation 1: Student must exist in the system
        Student student = studentManager.getStudent(studentID);
        if (student == null) {
            System.out.println("Error: Student with ID '" + studentID + "' not found in the system.");
            return false;
        }

        // Validation 2: Course must exist in the system
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Error: Course with code '" + courseCode + "' not found in the system.");
            return false;
        }

        // Validation 3: Prevent duplicate enrollment
        if (isEnrolled(studentID, courseCode)) {
            System.out.println("Error: Student '" + studentID + "' is already enrolled in course '" + courseCode + "'.");
            return false;
        }

        // Validation 4: Check array capacity
        if (enrollmentCount >= MAX_ENROLLMENTS) {
            System.out.println("Error: Enrollment list is full!");
            return false;
        }

        // All validations passed — create the enrollment relationship
        enrollmentStudentIDs[enrollmentCount] = studentID;
        enrollmentCourseCodes[enrollmentCount] = courseCode;
        enrollmentCount++;

        System.out.println("Success: Student '" + student.getFirstName() + " " + student.getLastName()
                + "' enrolled in '" + course.getCourseName() + "' (" + courseCode + ").");
        return true;
    }

    /**
     * Adds a student to a course, creating a new enrollment relationship.
     *
     * <p>This is the course-perspective equivalent of {@link #addCourseToStudent(String, String)}.
     * Internally, it delegates to {@code addCourseToStudent} since the relationship
     * is bidirectional — there is no difference in the stored data.</p>
     *
     * @param courseCode The code of the course
     * @param studentID  The ID of the student to add
     * @return {@code true} if the enrollment was created successfully; {@code false} otherwise
     */
    public boolean addStudentToCourse(String courseCode, String studentID) {
        // Delegate to addCourseToStudent — same relationship, different perspective
        return addCourseToStudent(studentID, courseCode);
    }

    /**
     * Checks whether a student is already enrolled in a specific course.
     *
     * <p>This is a private helper method used internally to prevent duplicate enrollment
     * records. It performs a linear search through the parallel arrays looking for a
     * matching student ID and course code pair.</p>
     *
     * @param studentID  The student ID to check
     * @param courseCode The course code to check
     * @return {@code true} if the student is already enrolled in the course;
     *         {@code false} otherwise
     */
    private boolean isEnrolled(String studentID, String courseCode) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentStudentIDs[i].equals(studentID) && enrollmentCourseCodes[i].equals(courseCode)) {
                return true; // Existing enrollment found
            }
        }
        return false; // No existing enrollment
    }

    /**
     * Finds and displays all courses a specific student is enrolled in.
     *
     * <p>Validates that the student exists, then searches the enrollment records for
     * all entries matching the given student ID. For each match, the corresponding
     * course details are displayed.</p>
     *
     * @param studentID The student ID to search enrollments for
     */
    public void findCourse(String studentID) {
        // Validate student exists before searching enrollments
        Student student = studentManager.getStudent(studentID);
        if (student == null) {
            System.out.println("Error: Student with ID '" + studentID + "' not found in the system.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentStudentIDs[i].equals(studentID)) {
                Course course = courseManager.getCourse(enrollmentCourseCodes[i]);
                if (course != null) {
                    System.out.println("Course found: " + course.getCourseCode() + " - "
                            + course.getCourseName() + " (" + course.getCourseType() + ")");
                    found = true;
                }
            }
        }

        // Edge case: Student exists but has no enrollments
        if (!found) {
            System.out.println("Student '" + studentID + "' is not enrolled in any course.");
        }
    }

    /**
     * Lists all courses a specific student is enrolled in, with detailed information.
     *
     * <p>Unlike {@link #findCourse(String)} which provides a brief summary, this method
     * presents a formatted, numbered list with course type and credit hour details.</p>
     *
     * @param studentID The student ID whose courses to list
     */
    public void listCourses(String studentID) {
        // Validate student exists
        Student student = studentManager.getStudent(studentID);
        if (student == null) {
            System.out.println("Error: Student with ID '" + studentID + "' not found in the system.");
            return;
        }

        System.out.println("=== Courses for Student: " + student.getFirstName() + " " + student.getLastName()
                + " (" + studentID + ") ===");
        boolean found = false;
        int courseNum = 1; // Numbering for the displayed list

        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentStudentIDs[i].equals(studentID)) {
                Course course = courseManager.getCourse(enrollmentCourseCodes[i]);
                if (course != null) {
                    System.out.println(courseNum + ". " + course.getCourseCode() + " - " + course.getCourseName()
                            + " | Type: " + course.getCourseType() + " | Credits: " + course.getCreditHours());
                    found = true;
                    courseNum++;
                }
            }
        }

        if (!found) {
            System.out.println("No courses enrolled.");
        }
    }

    /**
     * Finds and displays all students enrolled in a specific course.
     *
     * <p>Validates that the course exists, then searches the enrollment records for
     * all entries matching the given course code. For each match, the corresponding
     * student details are displayed.</p>
     *
     * @param courseCode The course code to search enrollments for
     */
    public void findStudent(String courseCode) {
        // Validate course exists before searching enrollments
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Error: Course with code '" + courseCode + "' not found in the system.");
            return;
        }

        boolean found = false;
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentCourseCodes[i].equals(courseCode)) {
                Student student = studentManager.getStudent(enrollmentStudentIDs[i]);
                if (student != null) {
                    System.out.println("Student found: " + student.getStudentID() + " - "
                            + student.getFirstName() + " " + student.getLastName());
                    found = true;
                }
            }
        }

        // Edge case: Course exists but has no enrolled students
        if (!found) {
            System.out.println("Course '" + courseCode + "' has no students enrolled.");
        }
    }

    /**
     * Lists all students enrolled in a specific course, with detailed information.
     *
     * <p>Unlike {@link #findStudent(String)} which provides a brief summary, this method
     * presents a formatted, numbered list with email contact details.</p>
     *
     * @param courseCode The course code whose students to list
     */
    public void listStudents(String courseCode) {
        // Validate course exists
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Error: Course with code '" + courseCode + "' not found in the system.");
            return;
        }

        System.out.println("=== Students in Course: " + course.getCourseName() + " (" + courseCode + ") ===");
        boolean found = false;
        int studentNum = 1; // Numbering for the displayed list

        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentCourseCodes[i].equals(courseCode)) {
                Student student = studentManager.getStudent(enrollmentStudentIDs[i]);
                if (student != null) {
                    System.out.println(studentNum + ". " + student.getStudentID() + " - "
                            + student.getFirstName() + " " + student.getLastName()
                            + " | Email: " + student.getEmail());
                    found = true;
                    studentNum++;
                }
            }
        }

        if (!found) {
            System.out.println("No students enrolled in this course.");
        }
    }

    /**
     * Displays all enrollment records in the system.
     *
     * <p>Each enrollment is displayed as a numbered entry showing the student ID, student name,
     * course code, and course name. If either the student or course referenced in an enrollment
     * record has been deleted from their respective manager, "Unknown" is displayed as a
     * fallback value.</p>
     */
    public void displayAllEnrollments() {
        System.out.println("=== All Enrollments ===");
        if (enrollmentCount == 0) {
            System.out.println("No enrollments available.");
            return;
        }

        for (int i = 0; i < enrollmentCount; i++) {
            // Look up student and course details for display
            Student student = studentManager.getStudent(enrollmentStudentIDs[i]);
            Course course = courseManager.getCourse(enrollmentCourseCodes[i]);

            // Use fallback "Unknown" if referenced entity has been deleted
            String studentDisplay = (student != null) ? student.getFirstName() + " " + student.getLastName() : "Unknown";
            String courseDisplay = (course != null) ? course.getCourseName() : "Unknown";

            System.out.println((i + 1) + ". Student: " + enrollmentStudentIDs[i] + " (" + studentDisplay
                    + ") <---> Course: " + enrollmentCourseCodes[i] + " (" + courseDisplay + ")");
        }
    }
}
