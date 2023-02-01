package com.foxminded.school.service;

import com.foxminded.school.entity.Course;
import com.foxminded.school.entity.Group;
import com.foxminded.school.entity.Student;
import com.foxminded.school.util.data.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataGeneratorService implements ApplicationRunner {
    private final Data data = new Data();

    private CourseService courseService;
    private GroupService groupService;
    private StudentService studentService;

    @Autowired
    public DataGeneratorService(CourseService courseService, GroupService groupService, StudentService studentService) {
        this.courseService = courseService;
        this.groupService = groupService;
        this.studentService = studentService;
    }

    public void insertTestDataInDatabase() {
        List<Group> groups = data.getGroups();
        groupService.saveAll(groups);

        List<Course> courses = data.getCourses();
        courseService.saveAll(courses);

        List<Student> students = data.getStudents(groups);
        Map<Student, List<Course>> studentsCourses = data.getStudentsCourses(students, courses);
        studentService.saveAll(students);
        studentService.assignToCourses(studentsCourses);
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (courseService.getAll().isEmpty() && groupService.getAll().isEmpty() && studentService.getAll().isEmpty()) {
            insertTestDataInDatabase();
        }
    }
}
