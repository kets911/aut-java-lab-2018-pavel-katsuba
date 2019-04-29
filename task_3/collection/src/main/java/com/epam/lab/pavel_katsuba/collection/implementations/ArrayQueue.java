package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

public class ArrayQueue<E> extends AbstractQueue<E> {
    private final static int DEFAULT_CAPACITY = 10;
    private E[] elements;

    @SuppressWarnings("unchecked")
    public ArrayQueue() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayQueue(int capacity) {
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public E peek() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return elements[0];
    }

    @Override
    public E poll() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        E element = elements[0];
        System.arraycopy(elements, 1, elements, 0, size-1);
        size--;
        return element;
    }

    @Override
    public E pull() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return elements[size-1];
    }

    @Override
    public E remove() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        size--;
        return elements[size];
    }

    @Override
    public void push(E element) {
        if (element == null){
            throw new IllegalArgumentException(Constants.NULL_ELEMENT_EXCEPTION);
        }
        if (size >= elements.length){
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        elements[size++] = element;
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
    public Iterator<E> getIterator() {
        return new ArrayQueueIter();
    }
    private class ArrayQueueIter implements Iterator<E>{
        private int recentIndex = -1;
        private int nextIndex = 0;
        @Override
        public E getNext() {
            if(recentIndex >= size){
                throw new IndexOutOfBoundsException(Constants.END_COLLECTION_EXCEPTION);
            }
            return elements[recentIndex = nextIndex++];
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
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
            int result = add(recentIndex, element);
            recentIndex = nextIndex++;
            return result;
        }

        @Override
        public int addAfter(E element) {
            return add(nextIndex, element);
        }

        private int add(int index, E element){
            if (element == null){
                throw new IllegalArgumentException(Constants.NULL_ELEMENT_EXCEPTION);
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
