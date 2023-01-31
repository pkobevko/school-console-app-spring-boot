package com.foxminded.school.service;

import com.foxminded.school.entity.Course;
import com.foxminded.school.entity.Student;
import lombok.NonNull;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student getById(int id);

    List<Student> getAll();

    void save(@NonNull Student student);

    void update(@NonNull Student student);

    void deleteById(int id);

    void assignToCourses(@NonNull Map<Student, List<Course>> studentsCourses);

    List<Student> getAllByCourseName(@NonNull String courseName);

    void assignToCourse(int studentId, int courseId);

    void deleteFromCourse(int studentId, int courseId);

    void saveAll(@NonNull List<Student> students);
}
