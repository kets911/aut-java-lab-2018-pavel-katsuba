package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

import java.util.Arrays;
import java.util.Comparator;

public class ArrayStack<E> extends AbstractStack<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;

    @SuppressWarnings("unchecked")
    public ArrayStack() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayStack(int capacity){
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public E peek() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return elements[size-1];
    }

    @Override
    public E pop() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        E element = elements[size-1];
        elements[size-1] = null;
        size--;
        return element;
    }

    @Override
    public void push(E element) {
        if (element == null){
            throw new IllegalArgumentException();
        }
        if (search(element) < 0){
            if (size >= elements.length){
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            elements[size++] = element;
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public int clear() {
        int result = size;
        elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
        return result;
    }

    @Override
    public void sort(Comparator<E> comparator) {
        if (comparator == null){
            throw new IllegalArgumentException(Constants.NULL_COMPARATOR_EXCEPTION);
        }
        elements = Arrays.copyOf(elements, size);
        Arrays.sort(elements, comparator);
    }

    @Override
    public Iterator<E> getIterator() {
        return new ArrayStackIterator();
    }

    private class ArrayStackIterator implements Iterator<E>{
        private int recentIndex = -1;
        private int nextIndex = size-1;
        @Override
        public E getNext() {
            if(recentIndex >= size){
                throw new IllegalArgumentException();
            }
            return elements[recentIndex = nextIndex--];
        }

        @Override
        public boolean hasNext() {
            return nextIndex > -1;
        }

        @Override
        public void remove() {
            if (recentIndex < 0){
                throw new IllegalStateException(Constants.REMOVE_EXCEPTION);
            }
            System.arraycopy(elements, recentIndex + 1, elements, recentIndex , size-1-recentIndex);
            nextIndex = recentIndex;
            recentIndex = -1;
            size--;
        }

        @Override
        public int addBefore(E element) {
            return add(recentIndex+1, element);
        }

        @Override
        public int addAfter(E element) {
            return add(nextIndex+1, element);
        }

        private int add(int index, E element){
            if (element == null){
                throw new IllegalArgumentException();
            }
            if (size >= elements.length){
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            System.arraycopy(elements, recentIndex, elements , recentIndex + 1, size + 1 - recentIndex);
            elements[index] = element;
            size++;
            return index;
        }
    }
}
