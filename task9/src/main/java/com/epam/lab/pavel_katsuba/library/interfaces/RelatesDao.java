package com.epam.lab.pavel_katsuba.library.interfaces;

import java.util.List;

public interface RelatesDao<T, E> {

    List<E> getBeans(int entityId);

    void addBeans(List<E> beans, int entityId);

    void putBeans(int entityId, int oldBeanId, int newBeanId);

    void deleteBeanRelates(int entityId);

    void delete(int entityId, int beanId);

    boolean relateIsExist(int entityId, int beanId);
}
