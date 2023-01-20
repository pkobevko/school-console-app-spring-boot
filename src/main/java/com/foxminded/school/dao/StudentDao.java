package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Student;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface StudentDao {
    Optional<Student> get(int id);

    List<Student> getAll();

    int save(@NonNull Student student);

    int update(@NonNull Student student);

    int delete(int id);

}
