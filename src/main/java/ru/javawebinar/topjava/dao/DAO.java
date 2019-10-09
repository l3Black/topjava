package ru.javawebinar.topjava.dao;

import java.util.List;

public interface DAO<T> {
    void editOrAdd(T entity);
    void delete(int id);
    List<T> getEntities();
    T getEntityById(int id);
}
