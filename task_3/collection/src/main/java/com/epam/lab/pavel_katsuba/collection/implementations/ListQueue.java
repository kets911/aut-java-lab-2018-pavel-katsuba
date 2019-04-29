package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.exceptions.EmptyCollectionException;
import com.epam.lab.pavel_katsuba.collection.interfaces.Iterator;

public class ListQueue<E> extends AbstractQueue<E> {
    private Node<E> top;
    private Node<E> tail;

    @Override
    public E peek() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return tail.element;
    }

    @Override
    public E poll() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        E result = tail.element;
        if (tail == top){
            tail = null;
            top = null;
        }else {
            tail = tail.prev;
            tail.next = null;
        }
        size--;
        return result;
    }

    @Override
    public E pull() {
        if (isEmpty()){
            throw new EmptyCollectionException(Constants.EMPTY_COLLECTION_EXCEPTION);
        }
        return top.element;
    }

    @Override
    public E remove() {
        if (isEmpty()){
            throw new IllegalArgumentException();
        }
        E result = top.element;
        if (tail == top){
            tail = null;
            top = null;
        }else {
            top = top.next;
            top.prev = null;
        }
        size--;
        return result;
    }

    @Override
    public void push(E element) {
        if (element == null){
            throw new IllegalArgumentException(Constants.NULL_ELEMENT_EXCEPTION);
        }
        if (isEmpty()){
            top =  new Node<>(element, null, null);
            tail = top;
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
        tail = null;
        size = 0;
        return result;
    }

    @Override
    public Iterator<E> getIterator() {
        return new ListQueueIter();
    }

    private class Node<E>{
        private E element;
        private Node<E> next;
        private Node<E> prev;

        public Node(E element, Node<E> next, Node<E> prev) {
            this.element = element;
            this.next = next;
            this.prev = prev;
        }
    }
    private class ListQueueIter implements Iterator<E>{
        private Node<E> lastReturned;
        private Node<E> next = tail;
        private int nextIndex;
        @Override
        public E getNext() {
            if (!hasNext()){
                throw new IllegalStateException(Constants.END_COLLECTION_EXCEPTION);
            }
            lastReturned = next;
            next = next.prev;
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
            if (size == 1){
                clear();
            }else {
                if (lastReturned.prev == null){
                    top = lastReturned.next;
                    top.prev = null;
                }else {
                    if (lastReturned.next == null){
                        tail = lastReturned.prev;
                        tail.next = null;
                    }else {
                        lastReturned.prev.next=lastReturned.next;
                        lastReturned.next.prev=lastReturned.prev;
                    }
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
            Node<E> newNode = new Node<>(element, null, lastReturned);
            if (lastReturned.next == null){
                tail = newNode;
                lastReturned.next = newNode;
            }else {
                newNode.next = lastReturned.next;
                lastReturned.next = newNode;
                newNode.next.prev = newNode;
            }
            size++;
            ++nextIndex;
            return nextIndex - 2;
        }

        @Override
        public int addAfter(E element) {
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
            return nextIndex++;
        }
    }
}
