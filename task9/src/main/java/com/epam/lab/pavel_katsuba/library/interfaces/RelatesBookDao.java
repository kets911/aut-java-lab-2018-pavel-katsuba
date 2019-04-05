package com.epam.lab.pavel_katsuba.library.interfaces;

import java.util.List;

public interface RelatesBookDao<T> {

    List<T> getEntities(int bookId);
    void addEntities(List<T> entities, int bookId);
    void putEntities(int bookId, int oldEntityId, int newEntityId);
    void deleteEntityRelate(int bookId);
    void delete(int entityId, int bookId);
}
