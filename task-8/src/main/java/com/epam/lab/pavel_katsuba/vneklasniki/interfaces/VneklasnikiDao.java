package com.epam.lab.pavel_katsuba.vneklasniki.interfaces;

import java.util.List;

public interface VneklasnikiDao<T> {
    int create(T entity);
    int getEntityId(T entity);
    List<T> getAllEntities();
    T getEntity(int entityId);
    boolean putEntity(int oldEntityId, T entity);
    boolean deleteEntity(int entityId);
}
