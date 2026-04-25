/**
 * Manages a collection of {@link Student} objects for the SLMS.
 * 
 * This class handles all core operations for student profiles. It utilizes a fixed-size 
 * array to store multiple student records and implements boundary checks to prevent 
 * out-of-bounds errors. Key functionalities include adding new students, performing 
 * linear searches by student ID, editing existing details, deleting records with 
 * array shifting, and displaying the complete list of enrolled students.
 * 
 */
public class StudentManager {
    // private data members (nabil)
    private Student[] studentList = new Student[50]; // Fixed array for storage
    private int studentCount = 0;

    // 1. Student Input Implementation (nabil)
    public void addStudent(Student newStudent) {
        // Check for null student
        if (newStudent == null) {
            System.out.println("Error: Student data cannot be null!");
            return;
        }

        // Check for duplicate student ID
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(newStudent.getStudentID())) {
                System.out.println("Error: Student with ID '" + newStudent.getStudentID() + "' already exists!");
                return;
            }
        }

        // Prevent array out-of-bound error
        if (studentCount >= studentList.length) {
            System.out.println("Error: Student list is full!");
            return;
        }
        
        studentList[studentCount] = newStudent;
        studentCount++;
        System.out.println("Student added successfully!");
    }

    // 2. Search Function Development (Wan)
    public Student searchStudent(String searchID) {
        // Validate input parameter
        if (searchID == null || searchID.trim().isEmpty()) {
            System.out.println("Error: Search ID cannot be empty.");
            return null;
        }

        // Implement a linear search function
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                return studentList[i]; // Return successful search
            }
        }
        
        System.out.println("Student not found"); 
        return null;
    }

    // Silent search (for internal use by EnrollmentManager - no print)
    public Student getStudent(String searchID) {
        // Validate input parameter
        if (searchID == null || searchID.trim().isEmpty()) {
            return null;
        }

        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                return studentList[i];
            }
        }
        return null;
    }
  
    // 3. Edit Function Development (Wan)
    public void editStudent(String searchID, String newFirstName, String newLastName, String newEmail, String newPhone) {
        Student targetStudent = searchStudent(searchID);
        
        if (targetStudent != null) {
            // Update all attributes except Student ID
            targetStudent.setFirstName(newFirstName);
            targetStudent.setLastName(newLastName);
            targetStudent.setEmail(newEmail);
            targetStudent.setPhoneNumber(newPhone);
            
            System.out.println("Student updated! Here are the validated changes:");
            targetStudent.displayStudent();
        }
    }


    // 4. Delete Function Development (Irfan)
    public void deleteStudent(String searchID) {
        // Validate input parameter
        if (searchID == null || searchID.trim().isEmpty()) {
            System.out.println("Error: Student ID cannot be empty.");
            return;
        }

        int targetIndex = -1;

        // Search for the target student to delete
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                targetIndex = i;
                break;
            }
        }

        if (targetIndex != -1) {
            // Confirm message simulation 
            System.out.println("Student " + searchID + " deleted.");
            
            // Shift elements to fill the gap
            for (int i = targetIndex; i < studentCount - 1; i++) {
                studentList[i] = studentList[i + 1];
            }
            
            studentList[studentCount - 1] = null;
            studentCount--;
            
            // Validate deletion by showing remaining students
            displayAllStudents();
        } else {
            System.out.println("Student not found");
        }
    }

    // 5. Data Display Function Development (Asyraf)
    public void displayAllStudents() {
        System.out.println("=== All Enrolled Students ===");
        if (studentCount == 0) {
            System.out.println("No students available.");
            return;
        }

        // Display all student attributes in an organized manner
        for (int i = 0; i < studentCount; i++) {
            studentList[i].displayStudent();
        }
    }

    // NEW: Get all student display strings for API auto-suggestion
    public String[] getAllStudentDisplayStrings() {
        String[] displays = new String[studentCount];
        for (int i = 0; i < studentCount; i++) {
            displays[i] = studentList[i].getStudentID() + " - " + studentList[i].getFirstName() + " " + studentList[i].getLastName();
        }
        return displays;
    }

    // NEW: Get all student IDs
    public String[] getAllStudentIDs() {
        String[] ids = new String[studentCount];
        for (int i = 0; i < studentCount; i++) {
            ids[i] = studentList[i].getStudentID();
        }
        return ids;
    }
}
