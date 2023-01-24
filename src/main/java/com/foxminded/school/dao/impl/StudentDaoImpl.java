package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.dao.rowmapper.StudentRowMapper;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.model.Student;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class StudentDaoImpl implements StudentDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, group_id, first_name, last_name FROM students WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, group_id, first_name, last_name FROM students;";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students(group_id, first_name, last_name) VALUES (?, ?, ?);";
    private static final String UPDATE_STUDENT_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM students WHERE id = ?;";
    private static final int NUMBER_OF_ROWS_SUCCESSFUL = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

    @Autowired
    public StudentDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Student get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, studentRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Student with id %d not found", id)));
    }

    @Override
    public List<Student> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, studentRowMapper);
    }

    @Override
    public boolean save(Student student) {
        int result = jdbcTemplate.update(INSERT_STUDENT_SQL, student.getGroupId(), student.getFirstName(), student.getLastName());
        return result == 1 ? true : false;
    }

    @Override
    public boolean update(Student student) {
        int result = jdbcTemplate.update(UPDATE_STUDENT_SQL, student.getGroupId(), student.getFirstName(), student.getLastName(), student.getId());
        return result == 1 ? true : false;
    }

    @Override
    public boolean delete(int id) {
        int result = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        if (result == NUMBER_OF_ROWS_SUCCESSFUL) {
            return true;
        } else {
            throw new NotFoundException(String.format("Student with id %d not found", id));
        }
    }
}
