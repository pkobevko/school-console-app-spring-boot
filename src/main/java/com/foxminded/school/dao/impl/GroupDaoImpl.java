package com.foxminded.school.dao.impl;

import com.foxminded.school.dao.GroupDao;
import com.foxminded.school.dao.rowmapper.GroupRowMapper;
import com.foxminded.school.model.Group;
import lombok.NonNull;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class GroupDaoImpl implements GroupDao {
    private static final String SELECT_BY_ID_SQL = "SELECT id, name FROM groups WHERE id = ?;";
    private static final String SELECT_ALL_SQL = "SELECT id, name FROM groups;";
    private static final String INSERT_GROUP_SQL = "INSERT INTO groups(name) VALUES (?);";
    private static final String UPDATE_GROUP_SQL = "UPDATE groups SET name = ? WHERE id = ?;";
    private static final String DELETE_BY_ID_SQL = "DELETE FROM groups WHERE id = ?;";

    private final JdbcTemplate jdbcTemplate;
    private final RowMapper<Group> groupRowMapper = new GroupRowMapper();

    public GroupDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public Optional<Group> get(int id) {
        return jdbcTemplate.query(SELECT_BY_ID_SQL, groupRowMapper, id)
            .stream()
            .findFirst();
    }

    @Override
    public List<Group> getAll() {
        return jdbcTemplate.query(SELECT_ALL_SQL, groupRowMapper);
    }

    @Override
    public int save(@NonNull Group group) {
        return jdbcTemplate.update(INSERT_GROUP_SQL, group.getName());
    }

    @Override
    public int update(@NonNull Group group) {
        return jdbcTemplate.update(UPDATE_GROUP_SQL, group.getName(), group.getId());
    }

    @Override
    public int delete(int id) {
        return jdbcTemplate.update(DELETE_BY_ID_SQL, id);
    }
}
