package com.foxminded.school.service.impl;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.entity.Course;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.service.CourseService;
import lombok.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CourseServiceImpl implements CourseService {
    private static final Logger LOGGER = LoggerFactory.getLogger(CourseServiceImpl.class);

    private final CourseDao courseDao;

    @Autowired
    public CourseServiceImpl(CourseDao courseDao) {
        this.courseDao = courseDao;
    }

    @Override
    public Course getById(int id) {
        return courseDao.get(id)
            .orElseThrow(() -> {
                LOGGER.error(String.format("Error finding course with id %d", id));
                throw new NotFoundException(String.format("Course with id %d not found", id));
            });
    }

    @Override
    public List<Course> getAll() {
        return courseDao.getAll();
    }

    @Override
    public void save(@NonNull Course course) {
        boolean saved = courseDao.save(course);
        if (!saved) {
            LOGGER.error(String.format("Error saving course %s", course));
            throw new IllegalStateException("Cannot save course");
        }
    }

    @Override
    public void update(@NonNull Course course) {
        Optional<Course> courseOptional = courseDao.get(course.getId());
        courseOptional.ifPresentOrElse(presentCourse -> {
            boolean updated = courseDao.update(course);
            if (!updated) {
                LOGGER.error(String.format("Error updating course %s", course));
                throw new IllegalStateException("Cannot update course");
            }
        }, () -> {
            LOGGER.error(String.format("Error finding course with id %d", course.getId()));
            throw new NotFoundException(String.format("Course with id %d not found", course.getId()));
        });
    }

    @Override
    public void deleteById(int id) {
        Optional<Course> courseOptional = courseDao.get(id);
        courseOptional.ifPresentOrElse(course -> {
            boolean deleted = courseDao.delete(id);
            if (!deleted) {
                LOGGER.error(String.format("Error deleting course with id %d", id));
                throw new IllegalStateException(String.format("Cannot delete course with id %d", id));
            }
        }, () -> {
            LOGGER.error(String.format("Error finding course with id %d", id));
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
            .orElseThrow(() -> {
                LOGGER.error(String.format("Error finding course with name \"%s\"", courseName));
                throw new NotFoundException(String.format("Course with name \"%s\" not found", courseName));
            });
    }

    @Override
    public void saveAll(@NonNull List<Course> courses) {
        courses.forEach(course -> save(course));
    }
}
