package com.foxminded.school.dao.rowmapper;

import com.foxminded.school.domain.model.Student;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class StudentRowMapper implements RowMapper<Student> {
    @Override
    public Student mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Student(
            rs.getInt("id"),
            rs.getInt("group_id"),
            rs.getString("first_name"),
            rs.getString("last_name")
        );
    }
}
