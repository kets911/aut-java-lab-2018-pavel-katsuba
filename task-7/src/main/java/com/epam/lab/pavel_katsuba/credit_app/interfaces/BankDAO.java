package com.epam.lab.pavel_katsuba.credit_app.interfaces;

import com.epam.lab.pavel_katsuba.credit_app.exceptions.DAOException;

import java.util.List;

public interface BankDAO<T> {
    void create(T entity);

    T read(int id);

    List<T> readAll();

    T update(int id, T entity);

    void delete(int id);

    default <E extends Identifiable> E read(int id, List<E> entities) {
        for (E entity : entities) {
            if (entity.getId() == id) {
                return entity;
            }
        }
        throw new DAOException("there is not entity with id = " + id);
    }
}
