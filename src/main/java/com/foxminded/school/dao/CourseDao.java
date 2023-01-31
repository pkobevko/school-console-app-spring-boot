package com.foxminded.school.dao;

import com.foxminded.school.entity.Course;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CourseDao extends Dao<Course> {
    List<Course> getAllByStudentId(int studentId);

    Optional<Course> getByName(@NonNull String courseName);
}
