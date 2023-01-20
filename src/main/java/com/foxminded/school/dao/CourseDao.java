package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Course;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    Optional<Course> get(int id);

    List<Course> getAll();

    int save(Course course);

    int update(Course course);

    int delete(int id);
}
