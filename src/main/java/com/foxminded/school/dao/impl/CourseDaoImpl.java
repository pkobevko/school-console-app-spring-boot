package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.rowmapper.CourseRowMapper;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.model.Course;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CourseDaoImpl implements CourseDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, name, description FROM courses WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name, description FROM courses;";
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses(name, description) VALUES (?, ?);";
    private static final String UPDATE_COURSE_SQL = "UPDATE courses SET name = ?, description = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM courses WHERE id = ?;";
    private static final int NUMBER_OF_ROWS_SUCCESSFUL = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

    @Autowired
    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Course get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, courseRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Course with id %d not found", id)));
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, courseRowMapper);
    }

    @Override
    public boolean save(@NonNull Course course) {
        int result = jdbcTemplate.update(INSERT_COURSE_SQL, course.getName(), course.getDescription());
        return result == 1 ? true : false;
    }

    @Override
    public boolean update(@NonNull Course course) {
        int result = jdbcTemplate.update(UPDATE_COURSE_SQL, course.getName(), course.getDescription(), course.getId());
        return result == 1 ? true : false;
    }

    @Override
    public boolean delete(int id) {
        int result = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        if (result == NUMBER_OF_ROWS_SUCCESSFUL) {
            return true;
        } else {
            throw new NotFoundException(String.format("Course with id %d not found", id));
        }
    }
}
