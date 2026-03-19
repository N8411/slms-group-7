/**
 * Represents a student profile within the Student Learning Management System (SLMS).
 * 
 * This class stores essential student details such as the student's first and last name, 
 * student ID, email, and phone number. It provides getter and setter methods to access 
 * and modify these attributes, with the exception of the student ID, which is set upon 
 * creation and cannot be edited later.
 * 
 */
public class Student {
    // private data members (wan - done)
    private String firstName;
    private String lastName;
    private String studentID; // No setter provided so it cannot be edited
    private String email;
    private String phoneNumber;

    // Student Constructor (wan - done)
    public Student(String firstName, String lastName, String studentID, String email, String phoneNumber) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.studentID = studentID;
        this.email = email;
        this.phoneNumber = phoneNumber;
    }

    // Getter methods (asyraaf)

    // Setter methods (asyraaf)

    // display student information (Irfan)
    public void displayStudent() {
        System.out.println("ID: " + studentID + " | Name: " + firstName + " " + lastName);
        System.out.println("Email: " + email + " | Phone: " + phoneNumber + "\n");
    }
}
