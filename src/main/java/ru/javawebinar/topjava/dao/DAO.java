package ru.javawebinar.topjava.dao;

import java.util.Collection;
import java.util.List;

public interface DAO<T> {
    void editOrSave(T entity);
    void delete(int id);
    Collection<T> getAll();
    T get(int id);
}
