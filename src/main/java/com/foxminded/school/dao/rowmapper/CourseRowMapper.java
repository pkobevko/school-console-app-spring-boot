package com.foxminded.school.dao.rowmapper;

import com.foxminded.school.model.Course;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class CourseRowMapper implements RowMapper<Course> {
    @Override
    public Course mapRow(ResultSet rs, int rowNum) throws SQLException {
        return new Course(
            rs.getInt("id"),
            rs.getString("name"),
            rs.getString("description")
        );
    }
}
