package com.foxminded.school.dao;

import com.foxminded.school.entity.Group;

import java.util.List;

public interface GroupDao extends Dao<Group> {
    List<Group> getAllByEqualOrLessStudentsCount(int studentsCount);
}
