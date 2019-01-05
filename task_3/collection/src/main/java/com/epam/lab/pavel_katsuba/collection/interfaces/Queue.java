package com.epam.lab.pavel_katsuba.collection.interfaces;

public interface Queue<E> extends Collection<E> {
    boolean isEmpty();
    E peek();
    E poll();
    E pull();
    E remove();
    void push(E element);
    void pushAll(Collection<? extends E> collection);
    void pushAll(E[] elements);
    int search(E element);
    int clear();
    int size();
}
