package com.epam.lab.pavel_katsuba.collection.interfaces;

public interface Iterator<T> {
    T getNext();
    boolean hasNext();
    void remove();
    int addBefore(T element);
    int addAfter(T element);
}
