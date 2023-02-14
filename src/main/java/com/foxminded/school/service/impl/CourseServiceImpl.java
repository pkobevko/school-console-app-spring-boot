package com.foxminded.school.service.impl;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.entity.Course;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.service.CourseService;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private final CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public Course getById(int id) {
        return courseDao.get(id)
            .orElseThrow(() -> new NotFoundException(String.format("Course with id %d not found", id)));
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public void save(@NonNull Course course) {
        boolean saved = courseDao.save(course);
        if (!saved) {
            throw new IllegalStateException("Cannot save course");
        }
    }

    @Override
    public void update(@NonNull Course course) {
        Optional<Course> courseOptional = courseDao.get(course.getId());
        courseOptional.ifPresentOrElse(presentCourse -> {
            boolean updated = courseDao.update(course);
            if (!updated) {
                throw new IllegalStateException("Cannot update course");
            }
        }, () -> {
            throw new NotFoundException(String.format("Course with id %d not found", course.getId()));
        });
    }

    @Override
    public void deleteById(int id) {
        Optional<Course> courseOptional = courseDao.get(id);
        courseOptional.ifPresentOrElse(course -> {
            boolean deleted = courseDao.delete(id);
            if (!deleted) {
                throw new IllegalStateException(String.format("Cannot delete course with id %d", id));
            }
        }, () -> {
            throw new NotFoundException(String.format("Course with id %d not found", id));
        });
    }

    @Override
    public List<Course> getAllByStudentId(int studentId) {
        return courseDao.getAllByStudentId(studentId);
    }

    @Override
    public Course getByName(@NonNull String courseName) {
        return courseDao.getByName(courseName)
            .orElseThrow(() -> new NotFoundException(String.format("Course with name \"%s\" not found", courseName)));
    }

    @Override
    public void saveAll(@NonNull List<Course> courses) {
        courses.forEach(course -> save(course));
    }
}
