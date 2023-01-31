package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.StudentDao;
import com.foxminded.school.dao.rowmapper.StudentRowMapper;
import com.foxminded.school.entity.Student;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class StudentDaoImpl implements StudentDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, group_id, first_name, last_name FROM students WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, group_id, first_name, last_name FROM students;";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO students(group_id, first_name, last_name) VALUES (?, ?, ?);";
    private static final String UPDATE_STUDENT_SQL = "UPDATE students SET group_id = ?, first_name = ?, last_name = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM students WHERE id = ?;";
    private static final String INSERT_STUDENTS_COURSES_SQL = "INSERT INTO students_courses(student_id, course_id) VALUES (?,?);";
    private static final String DELETE_STUDENT_FROM_COURSE_SQL = "DELETE FROM students_courses WHERE student_id = ? AND course_id = ?";
    private static final String SELECT_STUDENTS_BY_COURSE_NAME_SQL = "SELECT students.id, students.group_id, students.first_name, students.last_name "
                                                                     + "FROM students_courses INNER JOIN students ON students.id = students_courses.student_id "
                                                                     + "INNER JOIN courses ON courses.id = students_courses.course_id WHERE courses.name = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Student> studentRowMapper = new StudentRowMapper();

    @Autowired
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

    @Override
    public void saveAll(@NonNull List<Student> students) {
        students.forEach(student -> save(student));
    }

    @Override
    public List<Student> getAllByCourseName(@NonNull String courseName) {
        return jdbcTemplate.query(SELECT_STUDENTS_BY_COURSE_NAME_SQL, studentRowMapper, courseName);
    }

    @Override
    public int assignToCourse(int studentId, int courseId) {
        return jdbcTemplate.update(INSERT_STUDENTS_COURSES_SQL, studentId, courseId);
    }

    @Override
    public int deleteFromCourse(int studentId, int courseId) {
        return jdbcTemplate.update(DELETE_STUDENT_FROM_COURSE_SQL, studentId, courseId);
    }
}
