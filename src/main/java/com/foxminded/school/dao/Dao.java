package com.foxminded.school.dao;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

public interface Dao<T> {
    Optional<T> get(int id);

    List<T> getAll();

    boolean save(@NonNull T entity);

    boolean update(@NonNull T entity);

    boolean delete(int id);
}
