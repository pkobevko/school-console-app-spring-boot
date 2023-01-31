package com.foxminded.school.dao;

import com.foxminded.school.entity.Student;
import lombok.NonNull;

import java.util.List;

public interface StudentDao extends Dao<Student> {
    List<Student> getAllByCourseName(@NonNull String courseName);

    int assignToCourse(int studentId, int courseId);

    int deleteFromCourse(int studentId, int courseId);
}
