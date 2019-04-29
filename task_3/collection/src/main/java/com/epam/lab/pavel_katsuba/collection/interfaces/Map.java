package com.epam.lab.pavel_katsuba.collection.interfaces;


public interface Map<K, V> {
    boolean isEmpty();
    void set(K key, V value);
    void set(Entity<K, V> entity);
    Entity<K, V> remove(K key);
    Entity<K, V> remove(Entity<K, V> entity);
    List<K> getKeys();
    List<V> getValues();
    V get(K key);
    Entity<K, V> getEntity(K key);
    boolean contains(V value);
    int clear();
    int size();
    default  <T> void nullValidator(T value){
        if (value == null){
            throw new IllegalArgumentException();
        }
    }

    interface Entity<K, V> {
        K getKey();
        V getValue();
    }
}
