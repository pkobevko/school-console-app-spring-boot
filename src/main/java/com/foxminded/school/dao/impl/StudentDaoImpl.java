package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.dao.rowmapper.StudentRowMapper;
import com.foxminded.school.domain.model.Student;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.util.List;
import java.util.Optional;

public class StudentDaoImpl implements StudentDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, group_id, first_name, last_name FROM students WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, group_id, first_name, last_name FROM students;";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students(group_id, first_name, last_name) VALUES (?, ?, ?);";
    private static final String UPDATE_STUDENT_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM students WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Student> get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, studentRowMapper, id)
            .stream()
            .findFirst();
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, studentRowMapper);
    }

    @Override
    public int save(Student student) {
        return jdbcTemplate.update(INSERT_STUDENT_SQL, student.getGroupId(), student.getFirstName(), student.getLastName());
    }

    @Override
    public int update(Student student) {
        return jdbcTemplate.update(UPDATE_STUDENT_SQL, student.getGroupId(), student.getFirstName(), student.getLastName(), student.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
