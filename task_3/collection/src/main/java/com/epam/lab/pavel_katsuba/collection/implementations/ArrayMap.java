package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.interfaces.List;
import com.epam.lab.pavel_katsuba.collection.interfaces.Map;

public class ArrayMap<K, V> implements Map<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private ArrayMapEntity<K, V>[][] buckets;
    private int size;

    @SuppressWarnings("unchecked")
    public ArrayMap() {
        this.buckets = (ArrayMapEntity<K, V>[][]) new ArrayMapEntity[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    @SuppressWarnings("unchecked")
    public ArrayMap(int capacity) {
        this.buckets = (ArrayMapEntity<K, V>[][]) new ArrayMapEntity[capacity][capacity];
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void set(K key, V value) {
        set(new ArrayMapEntity<>(key, value));
    }

    @Override
    public void set(Entity<K, V> entity) {
        int index = hash(entity.getKey()) & (buckets.length-1);
        int i = 0;
        for (; i < buckets[index].length; i++){
            if (buckets[index][i] == null){
                buckets[index][i] = new ArrayMapEntity<>(entity.getKey(), entity.getValue());
                size++;
                return;
            }
            if (entity.getKey().equals(buckets[index][i].getKey())){
                throw new IllegalArgumentException();
            }
        }
        buckets[index] = Arrays.copyOf(buckets[index], buckets[index].length * 2);
        buckets[index][i] =  new ArrayMapEntity<>(entity.getKey(), entity.getValue());
        size++;
    }

    @Override
    public Entity<K, V> remove(K key) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        nullValidator(key);
        int index = hash(key) & (buckets.length-1);
        for (int i = 0; i < buckets[index].length; i++){
            if (buckets[index][i] == null){
                return null;
            }
            if (key.equals(buckets[index][i].getKey())){
                return remove(buckets[index], i);
            }
        }
        return null;
    }

    @Override
    public Entity<K, V> remove(Entity<K, V> entity) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        int index = hash(entity.getKey()) & (buckets.length-1);
        for (int i = 0; i < buckets[index].length; i++){
            if (buckets[index][i] == null){
                return null;
            }
            if ((buckets[index][i].equals(entity))){
                return remove(buckets[index], i);
            }
        }
        return null;
    }
    private Entity<K, V> remove(ArrayMapEntity<K, V>[] entities, int index){
        ArrayMapEntity<K, V> removedEntity = entities[index];
        System.arraycopy(entities, index + 1, entities, index , entities.length - 1 - index);
        int j = index + 1;
        for (; entities[j] != null; j++);
        entities[j - 1] = null;
        size--;
        return removedEntity;
    }

    @Override
    public List<K> getKeys() {
        List<K> keys = new ArrayList<>();
        for (ArrayMapEntity<K, V>[] entities : buckets) {
            for (ArrayMapEntity<K, V> entity : entities) {
                if (entity != null){
                    keys.add(entity.getKey());
                }
            }
        }
        return keys;
    }

    @Override
    public List<V> getValues() {
        List<V> values = new ArrayList<>();
        for (ArrayMapEntity<K, V>[] entities : buckets) {
            for (ArrayMapEntity<K, V> entity : entities) {
                if (entity != null){
                    values.add(entity.getValue());
                }
            }
        }
        return values;
    }

    @Override
    public V get(K key) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        nullValidator(key);
        int index = hash(key) & (buckets.length-1);
        for (int i = 0; i < buckets[index].length; i++){
            if (buckets[index][i] == null){
                return null;
            }
            if (buckets[index][i].getKey().equals(key)){
                return buckets[index][i].getValue();
            }
        }
        return null;
    }

    @Override
    public Entity<K, V> getEntity(K key) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        nullValidator(key);
        int index = hash(key) & (buckets.length-1);
        for (int i = 0; i < buckets[index].length; i++){
            if (buckets[index][i] == null){
                return null;
            }
            if (buckets[index][i].getKey().equals(key)){
                return buckets[index][i];
            }
        }
        return null;
    }

    @Override
    public boolean contains(V value) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        nullValidator(value);
        for (ArrayMapEntity<K, V>[] entities : buckets){
            for (ArrayMapEntity<K, V> entity : entities){
                if (entity != null && entity.getValue().equals(value)){
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    @SuppressWarnings("unchecked")
    public int clear() {
        int result = size;
        buckets = (ArrayMapEntity<K, V>[][]) new ArrayMapEntity[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        size = 0;
        return result;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(this.getClass().getSimpleName()+" -> ");
        stringBuilder.append('\n');
        for (ArrayMapEntity<K, V>[] entities : buckets){
            for (ArrayMapEntity<K, V> entity : entities){
                if (entity != null){
                    stringBuilder.append(entity).append(';').append('\n');
                }
            }
        }
        return stringBuilder.toString();
    }

    private int hash(K key) {
        int h;
        return (h = key.hashCode()) ^ (h >>> 16);
    }
    public static class ArrayMapEntity<K, V> implements Entity<K, V>{
        private final K key;
        private final V value;

        public ArrayMapEntity(K key, V value) {
            if (key == null || value == null){
                throw new IllegalArgumentException(Constants.NULL_MAP_ARGUMENTS_EXCEPTION);
            }
            this.key = key;
            this.value = value;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj){
                return true;
            }
            if (obj instanceof Entity){
                Entity other = (Entity) obj;
                return this.value.equals(other.getValue()) && this.key.equals(other.getKey());
            }
            return false;
        }

        @Override
        public String toString() {
            return "key: " + key + " value: " + value;
        }
    }
}
