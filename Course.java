public class Course {
    private String courseName;
    private String courseCode; // No setter provided so it cannot be edited
    private int creditHours;
    private String summary;
    private String msTeamsLink;

    public Course(String name, String code, int creditHours, String summary, String msTeamsLink) {
        this.courseName = name;
        this.courseCode = code;
        this.creditHours = creditHours;
        this.summary = summary;
        this.msTeamsLink = msTeamsLink;
    }

    // Getter methods
    public String getCourseName() { return courseName; }
    public String getCourseCode() { return courseCode; }
    public int getCreditHours() { return creditHours; }
    public String getSummary() { return summary; }
    public String getMsTeamsLink() { return msTeamsLink; }

    // Setter methods
    public void setCourseName(String courseName) { this.courseName = courseName; }
    public void setCreditHours(int creditHours) { this.creditHours = creditHours; }
    public void setSummary(String summary) { this.summary = summary; }
    public void setMsTeamsLink(String msTeamsLink) { this.msTeamsLink = msTeamsLink; }

    public void displayCourse() {
        System.out.println("Code: " + courseCode + " | Name: " + courseName + " | Credits: " + creditHours);
        System.out.println("Summary: " + summary);
        System.out.println("Teams Link: " + msTeamsLink + "\n");
    }
}
