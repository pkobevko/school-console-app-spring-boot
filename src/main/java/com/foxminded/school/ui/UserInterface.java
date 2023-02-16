package com.foxminded.school.ui;

import com.foxminded.school.entity.Course;
import com.foxminded.school.entity.Group;
import com.foxminded.school.entity.Student;
import com.foxminded.school.service.CourseService;
import com.foxminded.school.service.GroupService;
import com.foxminded.school.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Scanner;

@Component
public class UserInterface implements ApplicationRunner {
    private final Scanner scanner;
    private final CourseService courseService;
    private final GroupService groupService;
    private final StudentService studentService;

    @Autowired
    public UserInterface(CourseService courseService, GroupService groupService, StudentService studentService) {
        scanner = new Scanner(System.in);

        this.courseService = courseService;
        this.groupService = groupService;
        this.studentService = studentService;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        boolean exit = false;
        while (!exit) {
            printMainMenu();
            String input = scanner.next();
            System.out.println();
            if (input.equals("1")) {
                findGroupsByEqualOrLessStudentsCount();
            } else if (input.equals("2")) {
                findStudentsByCourseName();
            } else if (input.equals("3")) {
                addNewStudent();
            } else if (input.equals("4")) {
                deleteStudentById();
            } else if (input.equals("5")) {
                addStudentToCourse();
            } else if (input.equals("6")) {
                removeStudentCourse();
            } else if (input.equals("q")) {
                exit = true;
                System.out.println("Exiting...");
            }
        }
        scanner.close();
    }

    private void printMainMenu() {
        System.out.println();
        System.out.println("*** MAIN MENU ***");
        System.out.println("1. Find all groups with less or equals student count");
        System.out.println("2. Find all students related to course with given name");
        System.out.println("3. Add new student");
        System.out.println("4. Delete student by ID");
        System.out.println("5. Add a student to the course (from a list)");
        System.out.println("6. Remove the student from one of his or her courses");
        System.out.println("q. Exit program");
        System.out.print("Enter menu-letter >>> ");
    }

    private void findGroupsByEqualOrLessStudentsCount() {
        System.out.println("Find groups by max. students count: ");
        System.out.print("Enter students count >>> ");
        int studentCount = getNumber();

        System.out.println("List of groups:");
        List<Group> groups = groupService.getAllByEqualOrLessStudentsCount(studentCount);
        printGroups(groups);
    }

    private void findStudentsByCourseName() {
        System.out.println("Find students by course name:");
        System.out.print("Enter course name >>> ");
        String courseName = scanner.next();

        Course course = courseService.getByName(courseName);
        System.out.println("Students from course \"" + course.getName() + "\":");
        List<Student> students = studentService.getAllByCourseName(course.getName());
        printStudents(students);
    }

    private void addNewStudent() {
        System.out.println("Add new student:");
        System.out.print("Enter first name >>> ");
        String firstName = scanner.next();
        System.out.print("Enter last name >>> ");
        String lastName = scanner.next();

        Student student = new Student();
        student.setFirstName(firstName);
        student.setLastName(lastName);

        studentService.save(student);
    }

    private void deleteStudentById() {
        System.out.println("Delete student by ID:");
        printStudents(studentService.getAll());
        System.out.print("Enter student ID: ");
        int studentId = getNumber();
        studentService.deleteById(studentId);
    }

    private void addStudentToCourse() {
        System.out.println("Add student to course:");
        List<Student> students = studentService.getAll();
        printStudents(students);
        System.out.print("Enter student ID >>> ");
        int studentId = getNumber();

        List<Course> courses = courseService.getAll();
        printCourses(courses);
        System.out.print("Enter course ID >>> ");
        int courseId = getNumber();

        studentService.assignToCourse(studentId, courseId);
    }

    private void removeStudentCourse() {
        System.out.println("Remove student course:");
        List<Student> students = studentService.getAll();
        printStudents(students);
        System.out.print("Enter student ID >>> ");
        int studentId = getNumber();

        List<Course> studentCourses = courseService.getAllByStudentId(studentId);
        printCourses(studentCourses);

        System.out.print("Enter course ID >>> ");
        int courseId = getNumber();

        studentService.deleteFromCourse(studentId, courseId);
    }

    private int getNumber() {
        boolean numberIsIncorrect = true;
        int number = 0;
        while (numberIsIncorrect) {
            try {
                number = Integer.parseInt(scanner.next());
                System.out.println("Number entered: " + number);
                numberIsIncorrect = false;
            } catch (Exception e) {
                System.out.print("Error! Please enter number >>> ");
            }
        }
        return number;
    }

    private void printGroups(List<Group> groups) {
        for (Group group : groups) {
            printGroup(group);
        }
    }

    private void printGroup(Group group) {
        System.out.println(String.format("Group ID: %d | Group name: %s", group.getId(), group.getName()));
    }

    private void printStudents(List<Student> students) {
        for (Student student : students) {
            printStudent(student);
        }
    }

    private void printStudent(Student student) {
        String groupId = String.valueOf(student.getGroupId());
        if (student.getGroupId() == 0) {
            groupId = "W/O";
        }
        System.out.println(String.format("ID: %d | Group ID: %s | First name: %s | Last name: %s", student.getId(),
            groupId, student.getFirstName(), student.getLastName()));
    }

    private void printCourses(List<Course> courses) {
        for (Course course : courses) {
            printCourse(course);
        }
    }

    private void printCourse(Course course) {
        System.out.println(String.format("Course ID: %d | Course name: %s | Course description: %s", course.getId(),
            course.getName(), course.getDescription()));
    }
}