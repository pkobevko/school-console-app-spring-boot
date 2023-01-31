package com.foxminded.school.dao;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);

    List<T> getAll();

    int save(@NonNull T entity);

    int update(@NonNull T entity);

    int delete(int id);
}
