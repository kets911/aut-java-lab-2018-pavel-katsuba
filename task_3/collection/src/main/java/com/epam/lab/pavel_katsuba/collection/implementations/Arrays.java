package com.epam.lab.pavel_katsuba.collection.implementations;

import java.util.Comparator;

public class Arrays {
    @SuppressWarnings("unchecked")
    public static  <T> T[] copyOf(T[] elements, int newLength){
        T[] newElements = (T[]) new Object[newLength];
        for (int i = 0; i < elements.length; i++){
            if (i == newLength){
                return newElements;
            }
            newElements[i] = elements[i];
        }
        return newElements;
    }

    public static <T> void sort(T[] elements, Comparator<T> comparator){
        for (int i = elements.length - 1; i != 0; i--){
            for (int j = 0; j < i; j++){
                int compareRes = comparator.compare(elements[j], elements[j + 1]);
                if (compareRes > 0){
                    T element = elements[j];
                    elements[j] = elements[j + 1];
                    elements[j + 1] = element;
                }
            }
        }
    }
}
