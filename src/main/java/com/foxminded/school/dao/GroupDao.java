package com.foxminded.school.dao;

import com.foxminded.school.domain.model.Group;
import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface GroupDao {
    Optional<Group> get(int id);

    List<Group> getAll();

    int save(@NonNull Group group);

    int update(@NonNull Group group);

    int delete(int id);
}
