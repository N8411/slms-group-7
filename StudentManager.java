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
    public Student searchStudent(String searchID) {
        // Implement a linear search function
        for (int i = 0; i < studentCount; i++) {
            if (studentList[i].getStudentID().equals(searchID)) {
                return studentList[i]; // Return successful search
            }
        }
        
        System.out.println("Student not found"); 
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


    // 5. Data Display Function Development (Asyraf)

}
