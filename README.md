# EduTrack - Student Learning Management System (SLMS)

## Description

EduTrack is a Student Learning Management System (SLMS) designed to manage students, courses, assignments, and grades in an organized and efficient way.  

This system allows administrators, lecturers, and students to interact within a centralized platform.

## Documentation Formatting

### a) Main page:
    - The main page will display 3 button options to allow user choose their role
          whether they are a student, lecturer or admin
### b) Student / Lecturer / Admin Page:
    The page may contain multiple table of contents to separate different action categories
    called “section” (e.g. manage users and manage course will have their own table of
    contents).
    Structure of the page:
         1. Fixed Top Bar:
             - Contains back button for user to return to the main page
             - Contains pagination to show user where they are currently (e.g.,
                documentation > lecturer)
          2. Section title: size of H2 and Bold
          3. Section content: table of content that has 3 columns (numbering, action type
             and links to navigate to appropriate files (c))
             
## Use Case Design
<img width="1038" height="1040" alt="Screenshot 2026-02-23 205306" src="https://github.com/user-attachments/assets/2ed9ee0e-c72a-42a1-afd8-9c9c9337d237" />




## Files
Structure of every files:

### 1. Fixed Top Bar:
    a. Contains back button for user to return to previous page
    b. Contains pagination to show user where they are currently (e.g.,
       documentation > lecturer > add new course)
    c. Contains information on last updated date and who edited the
       documentation
### 2. File Content:
    a. Users must be guided step by step by using numbering.
    b. Each step will have their own title
    c. The title font style would be Underlined H
    d. Each Title may contain some parts that need be structured, use H3 font
       style for it
    e. Images to be displayed must be centered horizontally
    f. If the content has embedded links to navigate to, it must be displayed
       through popups


## Coding File Formatting
    ● Indentation: We will strictly use 4 spaces for indentation (no mixing tabs and spaces).
    ● Braces: Opening curly braces { will stay on the same line as the statement (e.g., if
       (condition) {), and closing braces } will be on a new line.
    ● Line Length: We will try to keep lines under 100 characters so we don't have to scroll
       horizontally.
    ● Whitespaces: Writing more than 1 space is not allowed even for assignment operator
       unless for indentation
       
## Naming Conventions
     All namings must be in English and appropriate
    ● File name: be short as possible, use lower case characters only, use underscore if it has
       multiple words (e.g. user_dashboard.tsx)
    ● Classes: PascalCase (e.g., CourseProfile, StudentManager).
    ● Variables and Methods: camelCase (e.g., studentName, getCourseCode()).
    ● Constants: UPPERCASE_WITH_UNDERSCORES (e.g., MAX_STUDENTS).
    ● Arrays/Lists: Plural names to show they hold multiple items (e.g., courseList, students).
    ● Booleans, null, undefined values: use upper case for TRUE, FALSE, NULL,
       UNDEFINED


## Function and Methods Modularity
    ● Single Responsibility: Every function should do only one thing. For example, a function
       that searches for a student should not also print the output.
    ● Function Length: We will aim to keep functions short, ideally under 40-50 lines. If a
       function gets too long, we will break it down into smaller helper methods.
    ● Naming and structure: The function should use descriptive names that clearly show
       purpose and use consistent namespace and prefixing for groups of functions. This is to
       help increase readability.
    ● Loose Coupling: Modules should have minimal dependencies on each other. A change
       in one module should ideally not require changes in others.
    ● camelCase: Function and method names will follow the camelCase convention instead
       of underscores. All functions will use meaningful verb-based names (e.g.,
       calculateGrade(), getScoret()). Consistency across the system will be strictly maintained.
    ● Clear Parameters: Parameter names should be meaningful and simple. For example,
       calculateGrade(int totalMarks, int assignmentWeight). The maximum parameters should
       have only 4 - 5 to avoid complexity, therefore it is recommended to use an object if too
       many.
       
## Continuous Integration Rules
    Since we are using GitHub to monitor the main branch, we will follow these steps:
    ● Branching: Nobody can push directly to the main branch except the team leader.
       Everyone must create a new branch for their features (e.g., feature/lab3-course-profile).
    ● Commits: Commit messages must be clear and describe what was changed (e.g., "Add
       linear search function for student ID").
    ● Pull Requests (PR): To merge into main, a PR must be opened. The team leader must
       review and authorize the merge to prevent breaking the main codebase.
    ● Task and issue management: Github issues will be used to help manage team tasks
       and track bugs. Each issue will be assigned to a specific team member, include a clear
       description of the task, and be linked to a corresponding feature branch. This ensures
       clear responsibility, better organization, and smooth collaboration among team members.
