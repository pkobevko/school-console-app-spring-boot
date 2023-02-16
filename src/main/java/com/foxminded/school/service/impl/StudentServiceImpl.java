package com.foxminded.school.service.impl;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.entity.Course;
import com.foxminded.school.entity.Student;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.service.StudentService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class StudentServiceImpl implements StudentService {
    private static final Logger LOGGER = LoggerFactory.getLogger(StudentServiceImpl.class);

    private final StudentDao studentDao;

    @Autowired
    public StudentServiceImpl(StudentDao studentDao) {
        this.studentDao = studentDao;
    }

    @Override
    public Student getById(int id) {
        return studentDao.get(id)
            .orElseThrow(() -> {
                LOGGER.error(String.format("Error finding student with id %d", id));
                throw new NotFoundException(String.format("Student with id %d not found", id));
            });
    }

    @Override
    public List<Student> getAll() {
        return studentDao.getAll();
    }

    @Override
    public void save(@NonNull Student student) {
        boolean saved = studentDao.save(student);
        if (!saved) {
            LOGGER.error(String.format("Error saving student %s", student));
            throw new IllegalStateException("Cannot save student");
        }
    }

    @Override
    public void update(@NonNull Student student) {
        Optional<Student> studentOptional = studentDao.get(student.getId());
        studentOptional.ifPresentOrElse(presentStudent -> {
            boolean updated = studentDao.update(student);
            if (!updated) {
                LOGGER.error(String.format("Error updating student %s", student));
                throw new IllegalStateException("Cannot update student");
            }
        }, () -> {
            LOGGER.error(String.format("Error finding student with id %d", student.getId()));
            throw new NotFoundException(String.format("Student with id %d not found", student.getId()));
        });
    }

    @Override
    public void deleteById(int id) {
        Optional<Student> studentOptional = studentDao.get(id);
        studentOptional.ifPresentOrElse(student -> {
            boolean deleted = studentDao.delete(id);
            if (!deleted) {
                LOGGER.error(String.format("Error deleting student with id %d", id));
                throw new IllegalStateException(String.format("Cannot delete student with id %d", id));
            }
        }, () -> {
            LOGGER.error(String.format("Error finding student with id %d", id));
            throw new NotFoundException(String.format("Student with id %d not found", id));
        });
    }

    @Override
    public void assignToCourses(@NonNull Map<Student, List<Course>> studentsCourses) {
        for (Map.Entry<Student, List<Course>> entry : studentsCourses.entrySet()) {
            Student student = entry.getKey();
            List<Course> courses = entry.getValue();
            for (Course course : courses) {
                assignToCourse(student.getId(), course.getId());
            }
        }
    }

    @Override
    public List<Student> getAllByCourseName(@NonNull String courseName) {
        return studentDao.getAllByCourseName(courseName);
    }

    @Override
    public void assignToCourse(int studentId, int courseId) {
        int result = studentDao.assignToCourse(studentId, courseId);
        if (result != 1) {
            LOGGER.error(String.format("Error assigning student with id %d to course with id %d", studentId, courseId));
            throw new IllegalStateException(String.format("Cannot assign student with id %d to course with id %d", studentId, courseId));
        }
    }

    @Override
    public void deleteFromCourse(int studentId, int courseId) {
        int result = studentDao.deleteFromCourse(studentId, courseId);
        if (result != 1) {
            LOGGER.error(String.format("Error deleting student with id %d from course with id %d", studentId, courseId));
            throw new IllegalStateException(String.format("Cannot delete student with id %d from course with id %d", studentId, courseId));
        }
    }

    @Override
    public void saveAll(@NonNull List<Student> students) {
        students.forEach(student -> save(student));
    }
}
