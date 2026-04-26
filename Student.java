/**
 * Represents a student profile within the Student Learning Management System (SLMS).
 *
 * <h2>Module Description</h2>
 * This class serves as the data model (entity) for a student. It encapsulates essential
 * student details including the student's first and last name, unique student ID, email,
 * and phone number. It provides getter and setter methods to access and modify these
 * attributes, following the JavaBeans convention.
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Student ID immutability:</b> The student ID is set only through the constructor
 *       and has no setter method. This enforces the business rule that a student's ID
 *       cannot be changed after creation, ensuring data integrity across the system.</li>
 *   <li><b>Separate first/last name fields:</b> Names are stored as separate fields rather
 *       than a single full name string. This allows for more flexible display formatting
 *       and easier sorting/searching by last name in future enhancements.</li>
 *   <li><b>Self-display method:</b> The {@link #displayStudent()} method is included in the
 *       model class itself following the principle of information expert — the class that
 *       owns the data is responsible for knowing how to present it.</li>
 *   <li><b>Private data members:</b> All fields are private to enforce encapsulation.
 *       External access is only possible through the provided getter and setter methods.</li>
 * </ul>
 *
 */
public class Student {

    /** The student's first name. */
    private String firstName;

    /** The student's last name. */
    private String lastName;

    /** The unique student identifier — immutable after construction. */
    private String studentID;

    /** The student's email address. */
    private String email;

    /** The student's phone number. */
    private String phoneNumber;

    /**
     * Constructs a new Student with the specified details.
     *
     * <p>All attributes are initialized upon creation. The student ID is set here
     * and cannot be modified afterwards.</p>
     *
     * @param firstName   The student's first name
     * @param lastName    The student's last name
     * @param studentID   The unique student identifier (immutable)
     * @param email       The student's email address
     * @param phoneNumber The student's phone number
     */
    public Student(String firstName, String lastName, String studentID, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // ==================== Getter Methods ====================

    /**
     * Returns the student's first name.
     *
     * @return the first name of the student
     */
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the student's last name.
     *
     * @return the last name of the student
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Returns the student's unique identifier.
     *
     * <p>Note: There is no setter for this field to enforce immutability.</p>
     *
     * @return the student ID
     */
    public String getStudentID() {
        return studentID;
    }

    /**
     * Returns the student's email address.
     *
     * @return the email address of the student
     */
    public String getEmail() {
        return email;
    }

    /**
     * Returns the student's phone number.
     *
     * @return the phone number of the student
     */
    public String getPhoneNumber() {
        return phoneNumber;
    }

    // ==================== Setter Methods ====================
    // Note: No setter for studentID — it is immutable after construction.

    /**
     * Sets the student's first name.
     *
     * @param firstName the new first name to set
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the student's last name.
     *
     * @param lastName the new last name to set
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    /**
     * Sets the student's email address.
     *
     * @param email the new email address to set
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Sets the student's phone number.
     *
     * @param phoneNumber the new phone number to set
     */
    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    // ==================== Display Method ====================

    /**
     * Displays the student's information in a formatted console output.
     *
     * <p>The output includes the student ID, full name, email, and phone number
     * arranged in a clear, organized two-line format for readability.</p>
     */
    public void displayStudent() {
        System.out.println("ID: " + studentID + " | Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email + " | Phone: " + phoneNumber + "\n");
    }
}
