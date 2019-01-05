package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.interfaces.Collection;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;
import com.epam.lab.pavel_katsuba.collection.interfaces.List;

import java.util.Arrays;
import java.util.Comparator;

public abstract class AbstractList<E> implements List<E> {
    protected final Comparator<? super E> defaultComparator =  new Comparator<E>() {
        @Override
        @SuppressWarnings("unchecked")
        public int compare(E thisEl, E thatEl) {
            if (thatEl == null && thisEl == null){
                return 0;
            }
            if (thisEl == null ){
                return 1;
            }
            if (thatEl == null){
                return -1;
            }
            return ((Comparable<E>) thisEl).compareTo(thatEl);
        }
    };
    protected Comparator<? super E> comparator;
    protected int size;
    protected int maxSize = -1;


    @Override
    public void filterMatches(Collection<E> collection) {
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()){
            int index = collection.find(iterator.getNext());
            if (index < 0){
                iterator.remove();
            }
        }
    }

    @Override
    public void filterMatches(E[] elements) {
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()){
            int index = Arrays.binarySearch(elements, iterator.getNext(), comparator);
            if (index < 0){
                iterator.remove();
            }
        }
    }

    @Override
    public void filterDifference(Collection<E> collection) {
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()){
            int index = collection.find(iterator.getNext());
            if (index >= 0){
                iterator.remove();
            }
        }
    }

    @Override
    public void filterDifference(E[] elements) {
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()){
            int index = Arrays.binarySearch(elements, iterator.getNext(), comparator);
            if (index > -1){
                iterator.remove();
            }
        }
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public int getMaxSize() {
        return maxSize;
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

    protected void indexValidate(int index){
        if (index < 0 || index >= size){
            throw new IllegalArgumentException("index must be from 0 to " + (size-1));
        }
    }
}
