package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.CourseDao;
import com.foxminded.school.dao.rowmapper.CourseRowMapper;
import com.foxminded.school.entity.Course;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class CourseDaoImpl implements CourseDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, name, description FROM courses WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name, description FROM courses;";
    private static final String INSERT_COURSE_SQL = "INSERT INTO courses(name, description) VALUES (?, ?);";
    private static final String UPDATE_COURSE_SQL = "UPDATE courses SET name = ?, description = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM courses WHERE id = ?;";
    private static final String SELECT_ALL_BY_STUDENT_ID_SQL = "SELECT courses.id, courses.name, courses.description " +
                                                               "FROM students_courses INNER JOIN courses ON courses.id = students_courses.course_id " +
                                                               "WHERE student_id = ?;";
    private static final String SELECT_BY_COURSE_NAME_SQL = "SELECT * FROM courses WHERE courses.name = ?;";
    private static final int ROWS_AFFECTED_ON_SUCCESSFUL_OPERATION = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Course> courseRowMapper = new CourseRowMapper();

    @Autowired
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
    public boolean save(@NonNull Course course) {
        return jdbcTemplate.update(INSERT_COURSE_SQL, course.getName(), course.getDescription()) == ROWS_AFFECTED_ON_SUCCESSFUL_OPERATION;
    }

    @Override
    public boolean update(@NonNull Course course) {
        return jdbcTemplate.update(UPDATE_COURSE_SQL, course.getName(), course.getDescription(), course.getId()) == ROWS_AFFECTED_ON_SUCCESSFUL_OPERATION;
    }

    @Override
    public boolean delete(int id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id) == ROWS_AFFECTED_ON_SUCCESSFUL_OPERATION;
    }

    @Override
    public List<Course> getAllByStudentId(int studentId) {
        return jdbcTemplate.query(SELECT_ALL_BY_STUDENT_ID_SQL, courseRowMapper, studentId);
    }

    @Override
    public Optional<Course> getByName(@NonNull String courseName) {
        return jdbcTemplate.query(SELECT_BY_COURSE_NAME_SQL, courseRowMapper, courseName)
            .stream()
            .findFirst();
    }
}
