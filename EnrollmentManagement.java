/**
 * Manages Course-Student relationships for the SLMS using parallel arrays.
 *
 * This class implements the core relationship management functions required
 * It uses two parallel arrays (enrollmentStudentIDs and enrollmentCourseCodes)
 * where each index i represents one enrollment: student enrollmentStudentIDs[i]
 * is enrolled in course enrollmentCourseCodes[i].
 *
 * Error handling covers: student/course not found, duplicate enrollment,
 * student without course, and course without student scenarios.
 *
 */
public class EnrollmentManager {
    // Parallel arrays for Course-Student relationships
    private String[] enrollmentStudentIDs;
    private String[] enrollmentCourseCodes;
    private int enrollmentCount;
    private static final int MAX_ENROLLMENTS = 500;
    
    // References to managers for validation
    private CourseManager courseManager;
    private StudentManager studentManager;
    
    // Constructor
     public EnrollmentManager(CourseManager cm, StudentManager sm) {
        enrollmentStudentIDs = new String[MAX_ENROLLMENTS];
        enrollmentCourseCodes = new String[MAX_ENROLLMENTS];
        enrollmentCount = 0;
        this.courseManager = cm;
        this.studentManager = sm;
    }
    
    // Add a course to a student (initiate Course-Student relationship)
    public boolean addCourseToStudent(String studentID, String courseCode) {
        // Validate student exists in the system

        // Validate course exists in the system

        // Check for duplicate enrollment (same student assigned to same course)

        // Check array bounds

        // Create the relationship

    }

    // Add a student to a course (initiate Course-Student relationship)
    public boolean addStudentToCourse(String courseCode, String studentID) {
        // Delegates to addCourseToStudent (same relationship, different perspective)
    }

    // Helper: Check if a student is already enrolled in a course
    private boolean isEnrolled(String studentID, String courseCode) {

    }

    // Find a student's course based on student's ID
    public void findCourse(String studentID) {
        // Validate student exists

        // Edge case: Student without an assigned course

    }

    // List all courses that a student is enrolled in
    public void listCourses(String studentID) {

    }

    // Find a student in a course based on course's code
    public void findStudent(String courseCode) {
        // Validate course exists

        // Edge case: Course without an assigned student
    }

    // List all students assigned to a specific course
    public void listStudents(String courseCode) {
      
    }

    // Display all enrollments
    public void displayAllEnrollments() {

    }
}
