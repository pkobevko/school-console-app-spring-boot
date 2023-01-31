package com.foxminded.school.service;

import com.foxminded.school.entity.Course;
import lombok.NonNull;

import java.util.List;

public interface CourseService {
    Course getById(int id);

    List<Course> getAll();

    void save(@NonNull Course course);

    void update(@NonNull Course course);

    void deleteById(int id);
}
