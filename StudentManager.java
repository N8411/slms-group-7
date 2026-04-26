/**
 * Manages a collection of {@link Student} objects for the SLMS.
 *
 * <h2>Module Description</h2>
 * This class handles all core CRUD (Create, Read, Update, Delete) operations for student
 * profiles. It utilizes a fixed-size array to store multiple student records and implements
 * boundary checks to prevent out-of-bounds errors. The manager acts as the business logic
 * layer between the presentation layer ({@link Main}) and the data model ({@link Student}).
 *
 * <h2>Design Decisions</h2>
 * <ul>
 *   <li><b>Fixed-size array:</b> A fixed-capacity array ({@code Student[50]}) was chosen over
 *       dynamic collections (like {@code ArrayList}) to demonstrate manual memory management
 *       and array boundary handling, as required by the lab specifications.</li>
 *   <li><b>Linear search:</b> A linear search algorithm is used for finding students by ID.
 *       This is appropriate given the small dataset size (max 50 students). For larger
 *       datasets, a HashMap would provide O(1) lookup performance.</li>
 *   <li><b>Dual search methods:</b> Two search methods are provided — {@link #searchStudent(String)}
 *       for UI-facing searches (prints "Student not found") and {@link #getStudent(String)} for
 *       internal lookups (returns null silently). This separation prevents unwanted console
 *       output when other modules (like EnrollmentManager) perform validation checks.</li>
 *   <li><b>Array shifting on delete:</b> When a student is deleted, all subsequent elements
 *       are shifted left to maintain a contiguous array. This prevents null gaps that would
 *       break the linear search and display functions.</li>
 *   <li><b>Student ID immutability:</b> The edit function does not allow modification of the
 *       student ID, as it serves as the unique identifier and primary key for all lookups.</li>
 * </ul>
 *
 */
public class StudentManager {

    /** Fixed-size array to store student objects. Maximum capacity: 50. */
    private Student[] studentList = new Student[50];

    /** Tracks the current number of students stored in the array. */
    private int studentCount = 0;

    /**
     * Adds a new student to the student list array.
     *
     * <p>Performs a boundary check to prevent array index out-of-bounds errors.
     * If the array is full, an error message is displayed and the operation is aborted.</p>
     *
     * @param newStudent The {@link Student} object to add to the list
     */
    public void addStudent(Student newStudent) {
        // Prevent array out-of-bound error by checking current count against capacity
        if (studentCount >= studentList.length) {
            System.out.println("Error: Student list is full!");
            return;
        }

        studentList[studentCount] = newStudent;
        studentCount++;
        System.out.println("Student added successfully!");
    }

    /**
     * Searches for a student by their student ID using linear search.
     *
     * <p>This is the UI-facing search method that prints "Student not found" when
     * no match is found. For internal use by other managers, use {@link #getStudent(String)}
     * instead to avoid unwanted console output.</p>
     *
     * @param searchID The student ID to search for
     * @return The matching {@link Student} object, or {@code null} if not found
     */
    public Student searchStudent(String searchID) {
        // Linear search: iterate through all stored students
        for (int i = 0; i < studentCount; i++) {
            // Use .equals() for String comparison, not == (reference comparison)
            if (studentList[i].getStudentID().equals(searchID)) {
                return studentList[i]; // Match found — return the student object
            }
        }

        // No match found — inform the user
        System.out.println("Student not found");
        return null;
    }

    /**
     * Silently retrieves a student by their ID without printing any messages.
     *
     * <p>This method is intended for internal use by other manager classes
     * (e.g., {@link EnrollmentManager}) that need to validate student existence
     * without producing console output.</p>
     *
     * @param searchID The student ID to look up
     * @return The matching {@link Student} object, or {@code null} if not found
     */
    public Student getStudent(String searchID) {
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                return studentList[i];
            }
        }
        return null; // Silent return — no console output
    }

    /**
     * Edits an existing student's profile information.
     *
     * <p>Locates the student using the provided ID, then updates all mutable attributes
     * (first name, last name, email, phone number). The student ID itself cannot be
     * changed, as it serves as the unique identifier.</p>
     *
     * <p>If the student is not found, the method relies on {@link #searchStudent(String)}
     * to display the appropriate error message.</p>
     *
     * @param searchID    The student ID of the student to edit
     * @param newFirstName  The new first name to set
     * @param newLastName   The new last name to set
     * @param newEmail      The new email address to set
     * @param newPhone      The new phone number to set
     */
    public void editStudent(String searchID, String newFirstName, String newLastName, String newEmail, String newPhone) {
        // Use the UI-facing search to locate the student (prints "not found" if absent)
        Student targetStudent = searchStudent(searchID);

        if (targetStudent != null) {
            // Update all attributes except Student ID (immutable)
            targetStudent.setFirstName(newFirstName);
            targetStudent.setLastName(newLastName);
            targetStudent.setEmail(newEmail);
            targetStudent.setPhoneNumber(newPhone);

            // Display the updated profile to validate changes
            System.out.println("Student updated! Here are the validated changes:");
            targetStudent.displayStudent();
        }
    }

    /**
     * Deletes a student from the list by their student ID.
     *
     * <p>The deletion process involves three steps:</p>
     * <ol>
     *   <li>Locate the target student's index in the array</li>
     *   <li>Shift all subsequent elements left to fill the gap</li>
     *   <li>Set the last occupied position to null and decrement the count</li>
     * </ol>
     *
     * <p>After deletion, all remaining students are displayed to validate the operation.</p>
     *
     * @param searchID The student ID of the student to delete
     */
    public void deleteStudent(String searchID) {
        int targetIndex = -1;

        // Step 1: Find the index of the target student
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                targetIndex = i;
                break; // Stop searching once found
            }
        }

        if (targetIndex != -1) {
            // Student found — proceed with deletion
            System.out.println("Student " + searchID + " deleted.");

            // Step 2: Shift elements left to fill the gap left by the deleted student
            for (int i = targetIndex; i < studentCount - 1; i++) {
                studentList[i] = studentList[i + 1];
            }

            // Step 3: Clear the last occupied position and decrement count
            studentList[studentCount - 1] = null;
            studentCount--;

            // Validate deletion by showing remaining students
            displayAllStudents();
        } else {
            System.out.println("Student not found");
        }
    }

    /**
     * Displays all students currently stored in the system.
     *
     * <p>If no students are available, a message is displayed indicating the empty state.
     * Otherwise, each student's information is displayed using the {@link Student#displayStudent()}
     * method.</p>
     */
    public void displayAllStudents() {
        System.out.println("=== All Enrolled Students ===");
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        // Iterate through all stored students and display their information
        for (int i = 0; i < studentCount; i++) {
            studentList[i].displayStudent();
        }
    }

    // ==================== API Support Methods ====================
    // These methods support the CacheAPI auto-suggestion feature.

    /**
     * Returns display-formatted strings for all students.
     *
     * <p>Each string follows the format: {@code "studentID - FirstName LastName"}.
     * Used by {@link CacheAPI} to generate auto-suggestion lists.</p>
     *
     * @return An array of formatted student display strings
     */
    public String[] getAllStudentDisplayStrings() {
        String[] displays = new String[studentCount];
        for (int i = 0; i < studentCount; i++) {
            displays[i] = studentList[i].getStudentID() + " - " + studentList[i].getFirstName() + " " + studentList[i].getLastName();
        }
        return displays;
    }

    /**
     * Returns all student IDs currently stored in the system.
     *
     * <p>Used by {@link CacheAPI} for auto-suggestion and search autocomplete features.</p>
     *
     * @return An array of all student IDs
     */
    public String[] getAllStudentIDs() {
        String[] ids = new String[studentCount];
        for (int i = 0; i < studentCount; i++) {
            ids[i] = studentList[i].getStudentID();
        }
        return ids;
    }
}
