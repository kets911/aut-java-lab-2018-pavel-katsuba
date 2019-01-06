package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Collection;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

import java.lang.reflect.Array;
import java.util.Comparator;

public class LinkedList<E> extends AbstractList<E> {
    private Node<E> head;

    public LinkedList(Comparator<E> comparator) {
        super.comparator = comparator;
    }


    public LinkedList() {
        super.comparator = super.defaultComparator;
    }


    @Override
    public E get(int index) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        Node<E> nodeStep = getNode(index);
        return nodeStep.element;
    }

    @Override
    public E set(int index, E element) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        E result = remove(index);
        add(element);
        return result;
    }

    private Node<E> getNode(int index){
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        Node<E> nodeStep = head;
        for (int i = 0; i<index; i++){
            nodeStep = nodeStep.next;
        }
        return nodeStep;
    }

    @Override
    public E remove(int index) {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        indexValidate(index);
        Iterator<E> iterator = getIterator();
        int i = 0;
        E element;
        while (i != index){
            iterator.getNext();
            i++;
        }
        element = iterator.getNext();
        iterator.remove();
        return element;
    }

    @Override
    public void trim() {
        if (size == 0){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        if (head.element == null){
            clear();
        } else {
            int index = 1;
            Node<E> i = head;
            for (; i.next != null; i = i.next){
                if (i.next.element == null){
                    i.next = null;
                    size = index;
                    return;
                }
                index++;
            }
        }
    }

    @Override
    public int add(E element) {
        if (maxSize > -1 && size == maxSize){
            throw new IllegalStateException(Constants.FULL_COLLECTION_EXCEPTION);
        }
        int index = 0;
        if (head == null){
            head = new Node<>(element, null, null);
            size++;
            return index;
        }
        for (Node<E> node = head; node!=null; node=node.next){
            int i = comparator.compare(element, node.element);
            if (i <= 0){
                if (node.prev == null){
                    Node<E> next =head;
                    head = new Node<>(element, next, null);
                    next.prev = head;
                    size++;
                    return index;
                }
                Node<E> newNode = new Node<>(element, node, node.prev);
                node.prev = newNode;
                newNode.prev.next = newNode;
                size++;
                return index;
            }
            index++;
        }
        Node<E> lastNode = getNode(size-1);
        lastNode.next = new Node<>(element, null, lastNode);
        size++;
        return ++index;
    }

    @Override
    public void addAll(Collection<? extends E> collection) {
        Iterator<? extends E> iterator = collection.getIterator();
        while (iterator.hasNext()){
            add(iterator.getNext());
        }
    }

    @Override
    public void addAll(E[] elements) {
        for (E element : elements){
            add(element);
        }
    }

    @Override
    public void clear() {
        head = null;
        size = 0;
    }

    @Override
    public int find(E element) {
        int index = 0;
        for (Node<E> i = head; i!=null; i=i.next){
            if (comparator.compare(element, i.element) == 0){
                return index;
            }
            index++;
        }
        return -1;
    }

    @Override
    @SuppressWarnings("unchecked")
    public E[] toArray(E[] elements) {
        if (size > elements.length){
            elements = (E[]) Array.newInstance(elements.getClass().getComponentType(), size);
        }
        Node<E> nodeStep = head;
        for (int i = 0; i< size; i++){
            elements[i] = nodeStep.element;
            nodeStep = nodeStep.next;
        }
        return elements;
    }

    @Override
    public void setMaxSize(int size) {
        this.maxSize = size;
        if (this.size > size){
            this.size = size;
            getNode(size-1).next = null;
        }
    }

    private void sort(){
        for (int i = size; i != 0; i--){
            Node<E> stepNode = head;
            for (int j = 1; j < i; j++){
                int compareRes = comparator.compare(stepNode.element, stepNode.next.element);
                if (compareRes > 0){
                    E element = stepNode.next.element;
                    stepNode.next.element = stepNode.element;
                    stepNode.element = element;
                }
                stepNode = stepNode.next;
            }
        }
    }

    @Override
    public Iterator<E> getIterator() {
        return new LinkedIterator();
    }

    private class Node<E>{
        E element;
        Node<E> next;
        Node<E> prev;

        Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }

    private class LinkedIterator implements Iterator<E>{
        private Node<E> lastReturned;
        private Node<E> next = head;
        private int nextIndex;
        private boolean neededSort;

        @Override
        public E getNext() {
            if (!hasNext()){
                throw new IllegalArgumentException();
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }

        @Override
        public boolean hasNext() {
            if (nextIndex == size){
                if (neededSort){
                    sort();
                }
                return false;
            }
            return true;
        }

        @Override
        public void remove() {
            if (lastReturned == null){
                throw new IllegalStateException(Constants.REMOVE_EXCEPTION);
            }
            if (lastReturned.prev == null){
                head = lastReturned.next;
                head.prev = null;
            }else {
                if (lastReturned.next == null){
                    lastReturned.prev.next = null;
                }else {
                    lastReturned.prev.next=lastReturned.next;
                    lastReturned.next.prev=lastReturned.prev;
                }
            }
            lastReturned = null;
            size--;
            nextIndex--;
        }

        @Override
        public int addBefore(E element) {
            if (lastReturned.prev != null){
                Node<E> node = new Node<>(element, lastReturned, lastReturned.prev);
                lastReturned.prev = node;
                node.prev.next = node;
            } else {
                lastReturned.prev = new Node<>(element, lastReturned, null);
                head = lastReturned.prev;
            }
            size++;
            int result = nextIndex - 1;
            nextIndex++;
            neededSort = true;
            return result;
        }

        @Override
        public int addAfter(E element) {
            if (lastReturned.next != null){
                Node<E> node = new Node<>(element, lastReturned.next, lastReturned);
                lastReturned.next = node;
                node.next.prev = node;
            } else {
                lastReturned.prev = new Node<>(element, null, lastReturned);
            }
            size++;
            neededSort = true;
            return nextIndex++;
        }
    }
}
