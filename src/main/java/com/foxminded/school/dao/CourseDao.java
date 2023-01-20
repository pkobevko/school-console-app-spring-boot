package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Course;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface CourseDao {
    Optional<Course> get(int id);

    List<Course> getAll();

    int save(@NonNull Course course);

    int update(@NonNull Course course);

    int delete(int id);
}
