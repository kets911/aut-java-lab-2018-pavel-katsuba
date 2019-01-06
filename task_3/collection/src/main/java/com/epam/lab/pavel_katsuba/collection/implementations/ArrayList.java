package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Collection;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

import java.lang.reflect.Array;
import java.util.Comparator;

public class ArrayList<E> extends AbstractList<E> {
    private static final int DEFAULT_CAPACITY = 10;
    private E[] elements;

    @SuppressWarnings("unchecked")
    public ArrayList() {
        super.comparator = super.defaultComparator;
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayList(Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayList(int capacity, Comparator<? super E> comparator) {
        this.comparator = comparator;
        this.elements = (E[]) new Object[capacity];
    }

    @Override
    public int add(E element) {
        if (maxSize > -1 && size == maxSize){
            throw new IllegalStateException(Constants.FULL_COLLECTION_EXCEPTION);
        }
        if (size == elements.length){
            elements = Arrays.copyOf(elements, elements.length * 2);
        }
        return add(element, 0, size);
    }
    private int add(E element, int startIndex, int lastIndex){
        if (size == 0){
            elements[0] = element;
            size++;
            return 0;
        }
        int middleIndex = (startIndex + lastIndex) / 2;
        int compareRes = comparator.compare(element, elements[middleIndex]);
        if (lastIndex - startIndex == 1){
            if (compareRes <= 0){
                System.arraycopy(elements, middleIndex, elements, middleIndex + 1 , size + 1 - middleIndex);
                elements[middleIndex] = element;
                size++;
                return middleIndex;
            }
            compareRes = comparator.compare(element, elements[middleIndex + 1]);
            System.arraycopy(elements, middleIndex, elements, middleIndex + 1 , size + 1 - middleIndex);
            if (compareRes <= 0){
                elements[middleIndex + 1] = element;
                size++;
                return middleIndex + 1;
            }
            elements[middleIndex + 2] = element;
            size++;
            return middleIndex + 2;
        }
        if (compareRes < 0){
            return add(element, startIndex, middleIndex);
        }
        if (compareRes > 0){
            return add(element, middleIndex, lastIndex);
        }
        System.arraycopy(elements, middleIndex, elements, middleIndex + 1 , size + 1 - middleIndex);
        elements[middleIndex] = element;
        size++;
        return middleIndex;
    }

    @Override
    public void addAll(Collection<? extends E> collection) {
        int collectionSize = collection.size();
        if (maxSize > 0 && size + collectionSize > maxSize){
            throw new IllegalStateException(Constants.FULL_COLLECTION_EXCEPTION);
        }
        if (size + collectionSize <= elements.length){
            elements = Arrays.copyOf(elements, elements.length + collectionSize * 2);
        }
        Iterator<? extends E> iterator = collection.getIterator();
        while (iterator.hasNext()){
            add(iterator.getNext(), 0, size);
        }
    }

    @Override
    public void addAll(E[] elements) {
        if (maxSize > 0 && size + elements.length > maxSize){
            throw new IllegalStateException(Constants.FULL_COLLECTION_EXCEPTION);
        }
        if (size + elements.length <= this.elements.length){
            this.elements = Arrays.copyOf(this.elements, this.elements.length + elements.length * 2);
        }
        for (E element : elements){
            add(element);
        }
    }

    @Override
    public E set(int index, E element) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        E recentElement = elements[index];
        elements[index] = element;
        Arrays.sort(elements, comparator);
        return recentElement;
    }

    @Override
    public E remove(int index) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        E element = elements[index];
        System.arraycopy(elements, index + 1, elements, index , size - 1 - index);
        size = size - 1;
        return element;
    }

    @Override
    @SuppressWarnings("unchecked")
    public void clear() {
        this.elements = (E[]) new Object[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public int find(E element) {
        return find(element, 0, size);
    }
    private int find(E element, int startIndex, int lastIndex){
        if (size == 0){
            return -1;
        }
        int middleIndex = (startIndex + lastIndex) / 2;
        int compareRes = comparator.compare(element, elements[middleIndex]);
        if (lastIndex - startIndex == 1){
            if (compareRes == 0){
                return middleIndex;
            }
            if (compareRes > 0){
                compareRes = comparator.compare(element, elements[middleIndex + 1]);
                if (compareRes == 0){
                    return middleIndex + 1;
                }
            }
            return -1;
        }
        if (compareRes < 0){
            return find(element, startIndex, middleIndex);
        }
        if (compareRes > 0){
            return find(element, middleIndex, lastIndex);
        }
        return middleIndex;
    }

    @Override
    public E get(int index) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        return elements[index];
    }

    @Override
    public E[] toArray(E[] elements) {
        if (size > elements.length){
            @SuppressWarnings("unchecked")
            E[] newElements = (E[]) Array.newInstance(elements.getClass().getComponentType(), size);
            System.arraycopy(this.elements, 0, newElements, 0, size);
            return newElements;
        }
        System.arraycopy(this.elements, 0, elements, 0, size);
        if (size < elements.length){
            for (int i = size; i < elements.length; i++){
                elements[i] = null;
            }
        }
        return elements;
    }

    @Override
    public void trim() {
        for (int i = 0; i < size; i++){
            if (elements[i] == null){
                size = i;
                return;
            }
        }
    }

    @Override
    public void setMaxSize(int size) {
        this.maxSize = size;
        if (this.size > size){
            this.size = size;
        }
    }

    @Override
    public Iterator<E> getIterator() {
        return new ArrayIterator();
    }
    private class ArrayIterator implements Iterator<E>{
        private int recentIndex = -1;
        private int nextIndex = 0;
        private boolean needSort;
        @Override
        public E getNext() {
            if(recentIndex >= size){
                throw new IndexOutOfBoundsException(Constants.END_COLLECTION_EXCEPTION);
            }
            return elements[recentIndex = nextIndex++];
        }

        @Override
        public boolean hasNext() {
            if (nextIndex == size){
                if (needSort){
                    Arrays.sort(elements, comparator);
                }
                return false;
            }
            return true;
        }

        @Override
        public void remove() {
            if (recentIndex < 0){
                throw new IllegalStateException(Constants.REMOVE_EXCEPTION);
            }
            ArrayList.this.remove(recentIndex);
            nextIndex = recentIndex;
            recentIndex = -1;
        }

        @Override
        public int addBefore(E element) {
            int result = recentIndex;
            recentIndex = nextIndex++;
            needSort = true;
            return add(result, element);
        }

        @Override
        public int addAfter(E element) {
            needSort = true;
            return add(nextIndex , element);
        }
        private int add(int index, E element){
            if (element == null){
                throw new IllegalArgumentException();
            }
            if (size >= elements.length){
                elements = Arrays.copyOf(elements, elements.length * 2);
            }
            System.arraycopy(elements, index, elements , index + 1, size + 1 - index);
            elements[index] = element;
            size++;
            return index;
        }
    }
}
