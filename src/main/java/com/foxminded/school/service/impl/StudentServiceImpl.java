package com.foxminded.school.service.impl;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.entity.Student;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.service.StudentService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getById(int id) {
        return studentDao.get(id)
            .orElseThrow(() -> new NotFoundException(String.format("Student with id %d not found", id)));
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public void save(@NonNull Student student) {
        int result = studentDao.save(student);
        if (result != 1) {
            throw new IllegalStateException("Cannot save student");
        }
    }

    @Override
    public void update(@NonNull Student student) {
        Optional<Student> studentOptional = studentDao.get(student.getId());
        studentOptional.ifPresentOrElse(presentStudent -> {
            int result = studentDao.update(student);
            if (result != 1) {
                throw new IllegalStateException("Cannot update student");
            }
        }, () -> {
            throw new NotFoundException(String.format("Student with id %d not found", student.getId()));
        });
    }

    @Override
    public void deleteById(int id) {
        Optional<Student> studentOptional = studentDao.get(id);
        studentOptional.ifPresentOrElse(student -> {
            int result = studentDao.delete(id);
            if (result != 1) {
                throw new IllegalStateException(String.format("Cannot delete student with id %d", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Student with id %d not found", id));
        });
    }
}
