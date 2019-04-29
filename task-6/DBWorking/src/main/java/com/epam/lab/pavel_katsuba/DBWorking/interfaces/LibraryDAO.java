package com.epam.lab.pavel_katsuba.DBWorking.interfaces;

import java.util.List;

public interface LibraryDAO<T> {
    void addEntity(T entity);
    List<T> getAllEntities();
    T getEntity(int id);
    T getEntity(String name);
    T putEntity(T entity, int oldId);
    void deleteEntity(int entityId);
}
