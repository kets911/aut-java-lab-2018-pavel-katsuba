package com.epam.lab.pavel_katsuba.collection.interfaces;

public interface Collection<E> extends Iterable<E> {
    int find(E element);
    int size();
}
