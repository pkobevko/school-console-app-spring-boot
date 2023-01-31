package com.foxminded.school.service;

import com.foxminded.school.entity.Student;
import lombok.NonNull;

import java.util.List;

public interface StudentService {
    Student getById(int id);

    List<Student> getAll();

    void save(@NonNull Student student);

    void update(@NonNull Student student);

    void deleteById(int id);
}
