# SLMS - Student Learning Management System

## Description

SLMS is a console-based Java application designed to manage students, courses, and their enrollments in an organized and efficient way.

This system provides administrators with a centralized platform to perform full CRUD operations across students, courses, and their many-to-many relationships — complete with a simulated caching and auto-suggestion API to streamline data entry.

> **Note:** This project was built and tested using [OnlineGDB](https://www.onlinegdb.com/) as the primary Java compiler/IDE.

---

## Features

### Course Management
Administrators can create new courses with attributes such as code, name, type (core/elective/university), credit hours, summary, and MS Teams link. Courses support linear search by code, field editing (course code is immutable), deletion with array-shifting, and a formatted list view.

### Student Management
Administrators can enroll new students with ID, name, email (format validated), and phone number. Students support linear search by ID, field editing (student ID is immutable), deletion with array-shifting, and a formatted list view.

### Enrollment Management
Uses parallel arrays to model a many-to-many relationship between students and courses. Supports enrolling a student into a course, duplicate prevention, querying all courses for a student, querying all students in a course, and a full enrollment mapping view.

### CacheAPI (Auto-Suggestion & Caching)
A simulated session-level caching utility that remembers recently entered Student IDs and Course Codes. Before prompting for input, the system displays existing records and recent cache entries to guide the user. Partial-string autocomplete is powered by Java's `String` API (`startsWith`, `toLowerCase`). Cache is cleared upon returning to the main menu to ensure data freshness.

---

## Tech Stack & Concepts

**Language:** Java (Standard Edition)  
**Environment:** OnlineGDB

**Data Structures:** Fixed-size 1D Arrays, Parallel Arrays, `ArrayList` (for caching)

**Core Concepts:**
- Object-Oriented Programming (Encapsulation, Separation of Concerns)
- Linear Search Algorithms
- Array Manipulation (deletion via element shifting)
- Input Validation & Exception Handling (`InputMismatchException`)
- Java Utility APIs (`java.util.Scanner`, `java.util.ArrayList`, `java.util.Arrays`)

---

## Getting Started (OnlineGDB)

Since this project uses multiple Java classes, follow these steps to run it on OnlineGDB:

1. Go to [OnlineGDB](https://www.onlinegdb.com/) and set the language to **Java**.
2. By default, OnlineGDB creates a single `Main.java` file.
3. Click the **"+"** (New File) tab and create the following files exactly as named:
   - `Student.java`
   - `StudentManager.java`
   - `Course.java`
   - `CourseManager.java`
   - `EnrollmentManager.java`
   - `CacheAPI.java`
4. Paste the respective source code from this repository into each file.
5. Click the green **Run** button. The console will start the program.

---

## Project Structure

```
SLMS/
├── Main.java               # Entry point, menu UI, and input handling
├── Student.java            # Student data model (entity)
├── Course.java             # Course data model (entity)
├── StudentManager.java     # Business logic for student array management
├── CourseManager.java      # Business logic for course array management
├── EnrollmentManager.java  # Business logic for course-student relationships (parallel arrays)
└── CacheAPI.java           # Simulated caching and auto-suggestion utility
```

---

## Usage Guide

Upon running the application, the main menu is displayed:

```
--- MAIN MENU ---
1. Course Management
2. Student Management
3. Enrollment Management
4. Exit
```

**Workflow example:**

1. Select `1` → Course Management → add a few courses.
2. Select `2` → Student Management → add a few students.
3. Select `3` → Enrollment Management.
4. Use option `1. Add Course to Student` to link them.
5. Use option `4. List All Courses for a Student` to view the relationship.

> Notice the `[Auto-Suggest]` and `[Cache]` prompts while entering IDs or Codes — these help you type correct values without memorizing them.

---

## Coding File Formatting

```
- Indentation:   4 spaces strictly (no mixing tabs and spaces)
- Braces:        Opening { stays on the same line as the statement; closing } on a new line
- Line Length:   Keep lines under 100 characters
- Whitespaces:   No more than 1 space allowed (except for indentation)
```

---

## Naming Conventions

```
- File names:      Lowercase only; use underscores for multiple words (e.g., user_dashboard.tsx)
- Classes:         PascalCase (e.g., CourseProfile, StudentManager)
- Variables/Methods: camelCase (e.g., studentName, getCourseCode())
- Constants:       UPPERCASE_WITH_UNDERSCORES (e.g., MAX_STUDENTS)
- Arrays/Lists:    Plural names (e.g., courseList, students)
- Booleans/null:   Use uppercase TRUE, FALSE, NULL, UNDEFINED
```

All names must be in English and semantically appropriate.

---

## Function and Method Modularity

```
- Single Responsibility:  Every function does one thing only
- Function Length:        Aim for under 40–50 lines; break down longer functions
- Naming:                 Use descriptive, verb-based camelCase names (e.g., calculateGrade(), getScore())
- Loose Coupling:         Minimize inter-module dependencies
- Parameters:             Meaningful names; max 4–5 params (use an object if more are needed)
```

---

## Continuous Integration Rules

```
- Branching:     No direct pushes to main; create feature branches (e.g., feature/lab3-course-profile)
- Commits:       Clear, descriptive messages (e.g., "Add linear search function for student ID")
- Pull Requests: A PR must be opened and reviewed by the team leader before merging
- Issue Tracking: GitHub Issues used for task assignment, descriptions, and branch linking
```

---

## Contributors

| Name | Contributions |
|---|---|
| Wan | Data member design, search function, edit function |
| Nabil | Data member design, student input/array implementation |
| Asyraaf | Getter/setter methods, data display function |
| Irfan | Display functions, delete function (array shifting logic) |

> `CacheAPI` and `EnrollmentManager` were integrated collaboratively to fulfill system relationship and API requirements.
