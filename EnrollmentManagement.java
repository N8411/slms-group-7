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
        Student student = studentManager.getStudent(studentID);
        if (student == null) {
            System.out.println("Error: Student with ID '" + studentID + "' not found in the system.");
            return false;
        }

        // Validate course exists in the system
        Course course = courseManager.getCourse(courseCode);
        if (course == null) {
            System.out.println("Error: Course with code '" + courseCode + "' not found in the system.");
            return false;
        }

        // Check for duplicate enrollment (same student assigned to same course)
        if (isEnrolled(studentID, courseCode)) {
            System.out.println("Error: Student '" + studentID + "' is already enrolled in course '" + courseCode + "'.");
            return false;
        }

        // Check array bounds
        if (enrollmentCount >= MAX_ENROLLMENTS) {
            System.out.println("Error: Enrollment list is full!");
            return false;
        }

        // Create the relationship
        enrollmentStudentIDs[enrollmentCount] = studentID;
        enrollmentCourseCodes[enrollmentCount] = courseCode;
        enrollmentCount++;

        System.out.println("Success: Student '" + student.getFirstName() + " " + student.getLastName()
                + "' enrolled in '" + course.getCourseName() + "' (" + courseCode + ").");
        return true;
    }

    // Add a student to a course (initiate Course-Student relationship)
    public boolean addStudentToCourse(String courseCode, String studentID) {
        // Delegates to addCourseToStudent (same relationship, different perspective)
        return addCourseToStudent(studentID, courseCode);
    }

    // Helper: Check if a student is already enrolled in a course
    private boolean isEnrolled(String studentID, String courseCode) {
        for (int i = 0; i < enrollmentCount; i++) {
            if (enrollmentStudentIDs[i].equals(studentID) && enrollmentCourseCodes[i].equals(courseCode)) {
                return true;
            }
        }
        return false;
    }

    // Find a student's course based on student's ID
    public void findCourse(String studentID) {
        // Validate student exists
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

        // Edge case: Student without an assigned course
        if (!found) {
            System.out.println("Student '" + studentID + "' is not enrolled in any course.");
        }
    }
    // List all courses that a student is enrolled in
    public void listCourses(String studentID) {
        Student student = studentManager.getStudent(studentID);
        if (student == null) {
            System.out.println("Error: Student with ID '" + studentID + "' not found in the system.");
            return;
        }

        System.out.println("=== Courses for Student: " + student.getFirstName() + " " + student.getLastName()
                + " (" + studentID + ") ===");
        boolean found = false;
        int courseNum = 1;

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
        

        if (!found) {
            System.out.println("No courses enrolled.");
        }
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
