package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.rowmapper.GroupRowMapper;
import com.foxminded.school.exception.NotFoundException;
import com.foxminded.school.model.Group;
import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GroupDaoImpl implements GroupDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, name FROM groups WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM groups;";
    private static final String INSERT_GROUP_SQL = "INSERT INTO groups(name) VALUES (?);";
    private static final String UPDATE_GROUP_SQL = "UPDATE groups SET name = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM groups WHERE id = ?;";
    private static final int NUMBER_OF_ROWS_SUCCESSFUL = 1;

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Group> groupRowMapper = new GroupRowMapper();

    @Autowired
    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Group get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, groupRowMapper, id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException(String.format("Group with id %d not found", id)));
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, groupRowMapper);
    }

    @Override
    public boolean save(@NonNull Group group) {
        int result = jdbcTemplate.update(INSERT_GROUP_SQL, group.getName());
        return result == 1 ? true : false;
    }

    @Override
    public boolean update(@NonNull Group group) {
        int result =jdbcTemplate.update(UPDATE_GROUP_SQL, group.getName(), group.getId());
        return result == 1 ? true : false;
    }

    @Override
    public boolean delete(int id) {
        int result = jdbcTemplate.update(DELETE_BY_ID_SQL, id);
        if (result == NUMBER_OF_ROWS_SUCCESSFUL) {
            return true;
        } else {
            throw new NotFoundException(String.format("Group with id %d not found", id));
        }
    }
}
