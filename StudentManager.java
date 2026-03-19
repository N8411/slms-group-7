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

    
    // 3. Edit Function Development (Wan)


    // 4. Delete Function Development (Irfan)
    public void deleteStudent(String searchID) {
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

}
