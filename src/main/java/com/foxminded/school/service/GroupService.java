package com.foxminded.school.service;

import com.foxminded.school.entity.Group;
import lombok.NonNull;

import java.util.List;

public interface GroupService {
    Group getById(int id);

    List<Group> getAll();

    void save(@NonNull Group group);

    void update(@NonNull Group group);

    void deleteById(int id);
}
