package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Group;

import java.util.List;
import java.util.Optional;

public interface GroupDao {
    Optional<Group> get(int id);

    List<Group> getAll();

    int save(Group group);

    int update(Group group);

    int delete(int id);
}
