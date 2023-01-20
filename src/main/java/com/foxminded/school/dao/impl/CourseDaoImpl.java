package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.rowmapper.CourseRowMapper;
import com.foxminded.school.domain.model.Course;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class CourseDaoImpl implements CourseDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, name, description FROM courses WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name, description FROM courses;";
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses(name, description) VALUES (?, ?);";
    private static final String UPDATE_COURSE_SQL = "UPDATE courses SET name = ?, description = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM courses WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

    public CourseDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Course> get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, courseRowMapper, id)
            .stream()
            .findFirst();
    }

    @Override
    public List<Course> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, courseRowMapper);
    }

    @Override
    public int save(Course course) {
        return jdbcTemplate.update(INSERT_COURSE_SQL, course.getName(), course.getDescription());
    }

    @Override
    public int update(Course course) {
        return jdbcTemplate.update(UPDATE_COURSE_SQL, course.getName(), course.getDescription(), course.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
