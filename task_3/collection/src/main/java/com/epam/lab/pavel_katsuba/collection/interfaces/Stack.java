package com.epam.lab.pavel_katsuba.collection.interfaces;

import java.util.Comparator;

public interface Stack<E> extends Collection<E>{
    boolean isEmpty();
    E peek();
    E pop();
    void push(E element);
    void pushAll(Collection<? extends E> collection);
    void pushAll(E[] elements);
    int search(E element);
    int clear();
    int size();
    void sort(Comparator<E> comparator);
}
