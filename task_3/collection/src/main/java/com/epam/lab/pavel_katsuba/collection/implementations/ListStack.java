package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

import java.util.Comparator;

public class ListStack<E> extends AbstractStack<E> {
    private Node<E> top;

    @Override
    public E peek() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return top.element;
    }

    @Override
    public E pop() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        E element = top.element;
        top = top.next;
        if (top != null){
            top.prev = null;
        }
        size--;
        return element;
    }

    @Override
    public void push(E element) {
        if (isEmpty()){
            top =  new Node<>(element, null, null);
        } else {
            Node<E> next = top;
            top = new Node<>(element, next, null);
            next.prev = top;
        }
        size++;
    }

    @Override
    public int clear() {
        int result = size;
        top = null;
        size = 0;
        return result;
    }

    @Override
    public void sort(Comparator<E> comparator) {
        if (comparator == null){
            throw new IllegalArgumentException(Constants.NULL_COMPARATOR_EXCEPTION);
        }
        Node<E> newTop = null;
        Iterator<E> iterator = getIterator();
        while (iterator.hasNext()){
            if (newTop == null){
                newTop = new Node<>(iterator.getNext(), null, null);
            } else {
                E element = iterator.getNext();
                Node<E> stepNode = newTop;
                while (stepNode != null){
                    int i = comparator.compare(element, stepNode.element);
                    if (i > 0){
                        if (stepNode.prev == null){
                            Node<E> next =newTop;
                            newTop = new Node<>(element, next, null);
                            next.prev = newTop;
                        } else {
                            Node<E> newNode = new Node<>(element, stepNode, stepNode.prev);
                            stepNode.prev = newNode;
                            newNode.prev.next = newNode;
                        }
                        break;
                    }
                    if (stepNode.next == null){
                        stepNode.next = new Node<>(element, null, stepNode);
                        break;
                    }
                    stepNode = stepNode.next;
                }
            }
        }
        top = newTop;

//        sort(top, size, comparator);
    }

    @Override
    public Iterator<E> getIterator() {
        return new ListStackIter();
    }

    private class Node<E>{
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            if (element == null){
                throw new NullPointerException(Constants.NULL_ELEMENT_EXCEPTION);
            }
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
    private class ListStackIter implements Iterator<E>{
        private Node<E> lastReturned;
        private Node<E> next = top;
        private int nextIndex;
        @Override
        public E getNext() {
            if (!hasNext()){
                throw new IndexOutOfBoundsException(Constants.END_COLLECTION_EXCEPTION);
            }
            lastReturned = next;
            next = next.next;
            nextIndex++;
            return lastReturned.element;
        }

        @Override
        public boolean hasNext() {
            return nextIndex < size;
        }

        @Override
        public void remove() {
            if (lastReturned == null){
                throw new IllegalStateException(Constants.REMOVE_EXCEPTION);
            }
            if (lastReturned.prev == null){
                top = lastReturned.next;
                top.prev = null;
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
            if (element == null){
                throw new IllegalArgumentException(Constants.NULL_ELEMENT_EXCEPTION);
            }
            if (lastReturned.prev == null){
                push(element);
            }else {
                Node<E> newNode = new Node<>(element, lastReturned, lastReturned.prev);
                newNode.prev.next = newNode;
                lastReturned.prev = newNode;
                size++;
            }
            nextIndex++;
            return nextIndex;
        }

        @Override
        public int addAfter(E element) {
            if (element == null){
                throw new IllegalArgumentException(Constants.NULL_ELEMENT_EXCEPTION);
            }
            Node<E> newNode = new Node<>(element, null, lastReturned);
            if (lastReturned.next == null){
                lastReturned.next = newNode;
            }else {
                newNode.next = lastReturned.next;
                lastReturned.next = newNode;
                newNode.next.prev = newNode;
            }
            next = newNode;
            size++;
            return nextIndex+1;
        }
    }
}
