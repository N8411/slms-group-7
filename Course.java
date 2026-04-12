/**
 * Represents a course profile within the Student Learning Management System (SLMS).
 *
 * This class serves as the data model for a course, storing attributes such as the 
 * course name, unique course code, course type, credit hours, summary, and MS Teams link. 
 * It provides necessary getter and setter methods for data manipulation. 
 * Note that the course code is initialized upon creation and cannot be modified later.
 *
 */
public class Course {
    // private data members
    private String courseName;
    private String courseCode; // No setter provided so it cannot be edited
     private String courseType; // core, elective, university
    private int creditHours;
    private String summary;
    private String msTeamsLink;

    // Course Constructor
    public Course(String name, String code, String courseType, int creditHours, String summary, String msTeamsLink) {
        this.courseName = name;
        this.courseCode = code;
        this.courseType = courseType;
        this.creditHours = creditHours;
        this.summary = summary;
        this.msTeamsLink = msTeamsLink;
    }

    // Getter methods
    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }
    public String getCourseType() { return courseType; }
    public int getCreditHours() { return creditHours; }
    public String getSummary() { return summary; }
    public String getMsTeamsLink() { return msTeamsLink; }

    // Setter methods
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCourseType(String courseType) { this.courseType = courseType; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setMsTeamsLink(String msTeamsLink) { this.msTeamsLink = msTeamsLink; }

    // Display course information
    public void displayCourse() {
        System.out.println("Code: " + courseCode + " | Name: " + courseName + " | Type: " + courseType + " | Credits: " + creditHours);
        System.out.println("Summary: " + summary);
        System.out.println("Teams Link: " + msTeamsLink + "\n");
    }
}
