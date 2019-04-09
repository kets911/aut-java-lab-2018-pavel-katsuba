package com.epam.lab.pavel_katsuba.library.interfaces;

import java.util.List;

public interface RelatesDao<T, E> {

    List<T> getEntities(int beanId);
    List<E> getBeans(int entityId);
    void addEntities(List<T> entities, int beanId);
    void addBeans(List<E> beans, int entityId);
    void putEntities(int beanId, int oldEntityId, int newEntityId);
    void putBeans(int entityId, int oldBeanId, int newBeanId);
    void deleteEntityRelates(int beanId);
    void deleteBeanRelates(int entityId);
    void delete(int entityId, int beanId);
}
