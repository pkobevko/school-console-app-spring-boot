package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Student;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> get(int id);

    List<Student> getAll();

    int save(Student student);

    int update(Student student);

    int delete(int id);

}
