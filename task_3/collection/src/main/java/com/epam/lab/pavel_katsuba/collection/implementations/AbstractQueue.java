package com.epam.lab.pavel_katsuba.collection.implementations;


import com.epam.lab.pavel_katsuba.collection.interfaces.Collection;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;
import com.epam.lab.pavel_katsuba.collection.interfaces.Queue;

public abstract class AbstractQueue<E> implements Queue<E> {
    protected int size;

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public int search(E element) {
        Iterator<E> iterator = getIterator();
        int index = 0;
        while (iterator.hasNext()){
            if (iterator.getNext().equals(element)){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    public void pushAll(Collection<? extends E> collection) {
        Iterator<? extends E> iterator = collection.getIterator();
        while (iterator.hasNext()){
            push(iterator.getNext());
        }
    }

    @Override
    public void pushAll(E[] elements) {
        for (E element : elements){
            push(element);
        }
    }

    @Override
    public int find(E element) {
        return search(element);
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        Iterator<E> iterator = getIterator();
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName()+" -> ");
        while (iterator.hasNext()){
            stringBuilder.append(iterator.getNext()).append(';').append('\n');
        }
        return stringBuilder.toString();
    }

}
