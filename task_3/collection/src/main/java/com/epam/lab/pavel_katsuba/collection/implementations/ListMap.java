package com.epam.lab.pavel_katsuba.collection.implementations;

import com.epam.lab.pavel_katsuba.collection.Constants;
import com.epam.lab.pavel_katsuba.collection.interfaces.List;
import com.epam.lab.pavel_katsuba.collection.interfaces.Map;

public class ListMap<K, V> implements Map<K, V> {
    private int size;
    private EntityListMap<K, V> head;

    public ListMap() {
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public void set(K key, V value) {
        set(new EntityListMap<>(key, value));
    }

    @Override
    public void set(Entity<K, V> entity) {
        nullValidator(entity.getKey());
        nullValidator(entity.getValue());
        int hash = hash(entity.getKey());
        if (head == null){
            head = new EntityListMap<>(entity.getKey(), entity.getValue());
            head.setHash(hash);
            size++;
        }else {
            EntityListMap<K, V> nextStep = head;
            EntityListMap<K, V> prevEntity = null;
            for (; nextStep != null; nextStep = nextStep.next){
                if (nextStep.getHash() == hash){
                    EntityListMap<K, V> downStep = nextStep;
                    if (downStep.getKey().equals(entity.getKey())){
                        throw new IllegalArgumentException();
                    }
                    while (downStep.down != null){
                        if (downStep.down.getKey().equals(entity.getKey())){
                            throw new IllegalArgumentException();
                        }
                        downStep =downStep.down;
                    }
                    downStep.down =  new EntityListMap<>(entity.getKey(), entity.getValue());
                    downStep.down.setHash(hash);
                    size++;
                    return;
                }
                prevEntity = nextStep;
            }
            prevEntity.next = new EntityListMap<>(entity.getKey(), entity.getValue());
            prevEntity.next.setHash(hash);
            prevEntity.next.prev = prevEntity;
            size++;
        }

    }

    @Override
    public Entity<K, V> remove(K key) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        nullValidator(key);
        int hash = hash(key);
        EntityListMap<K, V> nextStep = head;
        for (; nextStep != null; nextStep = nextStep.next){
            if(nextStep.getHash() == hash){
                if (nextStep.getKey().equals(key)){
                    return removeFromTop(nextStep);
                }
                EntityListMap<K, V> downStep = nextStep;
                while (downStep.down != null){
                    if (downStep.down.getKey().equals(key)){
                        EntityListMap<K, V> result = downStep.down;
                        downStep.down = downStep.down.down;
                        size--;
                        return result;
                    }
                    downStep = downStep.down;
                }
                return null;
            }
        }
        return null;
    }

    @Override
    public Entity<K, V> remove(Entity<K, V> entity) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        K key = entity.getKey();
        nullValidator(entity.getValue());
        nullValidator(key);
        int hash = hash(key);
        EntityListMap<K, V> nextStep = head;
        for (; nextStep != null; nextStep = nextStep.next){
            if(nextStep.getHash() == hash){
                if (nextStep.equals(entity)){
                   return removeFromTop(nextStep);
                }
                EntityListMap<K, V> downStep = nextStep;
                while (downStep.down != null){
                    if (downStep.down.equals(entity)){
                        EntityListMap<K, V> result = downStep.down;
                        downStep.down = downStep.down.down;
                        size--;
                        return result;
                    }
                    downStep = downStep.down;
                }
                return null;
            }
        }
        return null;
    }
    private Entity<K, V> removeFromTop(EntityListMap<K, V> nextStep){
        if (nextStep.prev == null){
            if (nextStep.down == null){
                head = nextStep.next;
                nextStep.next.prev = head;
            } else {
                head = nextStep.down;
                nextStep.down.prev = head;
                nextStep.down.next = nextStep.next;
            }
            size--;
            return nextStep;
        }
        if (nextStep.next == null){
            if (nextStep.down == null){
                nextStep.prev.next = null;
            } else {
                nextStep.prev.next = nextStep.down;
                nextStep.down.prev = nextStep.prev;
            }
            size--;
            return nextStep;
        }
        if (nextStep.down == null){
            nextStep.prev.next = nextStep.next;
            nextStep.next.prev = nextStep.prev;
        } else {
            nextStep.prev.next = nextStep.down;
            nextStep.down.prev = nextStep.prev;
            nextStep.down.next = nextStep.next;
        }
        size--;
        return nextStep;
    }

    @Override
    public List<K> getKeys() {
        List<K> keys = new ArrayList<>();
        for (EntityListMap<K, V> nextStep = head; nextStep != null; nextStep = nextStep.next){
            keys.add(nextStep.getKey());
            for (EntityListMap<K, V> downStep = nextStep; downStep.down != null; downStep = downStep.down){
                keys.add(downStep.down.getKey());
            }
        }
        return keys;
    }

    @Override
    public List<V> getValues() {
        List<V> values = new ArrayList<>();
        for (EntityListMap<K, V> nextStep = head; nextStep != null; nextStep = nextStep.next){
            values.add(nextStep.getValue());
            for (EntityListMap<K, V> downStep = nextStep; downStep.down != null; downStep = downStep.down){
                values.add(downStep.down.getValue());
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
        int hash = hash(key);
        for (EntityListMap<K, V> nextStep = head; nextStep != null; nextStep = nextStep.next){
            if (nextStep.hash == hash){
                return nextStep.getValue();
            }
            for (EntityListMap<K, V> downStep = nextStep; downStep.down != null; downStep = downStep.down){
                if (downStep.hash == hash){
                    return downStep.getValue();
                }
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
        int hash = hash(key);
        for (EntityListMap<K, V> nextStep = head; nextStep != null; nextStep = nextStep.next){
            if (nextStep.hash == hash){
                return nextStep;
            }
            for (EntityListMap<K, V> downStep = nextStep; downStep.down != null; downStep = downStep.down){
                if (downStep.hash == hash){
                    return downStep;
                }
            }
        }
        return null;
    }

    @Override
    public boolean contains(V value) {
        if (isEmpty()){
            throw new  IllegalStateException(Constants.EMPTY_MAP_EXCEPTION);
        }
        for (EntityListMap nextStep = head; nextStep != null; nextStep = nextStep.next){
            if (nextStep.getValue().equals(value)){
                return true;
            }
            for (EntityListMap downStep = nextStep; downStep.down != null; downStep = downStep.down){
               if (downStep.getValue().equals(value)){
                   return true;
               }
            }
        }
        return false;
    }

    @Override
    public int clear() {
        int result = size;
        head = null;
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
        for (EntityListMap<K, V> nextStep = head; nextStep != null ; nextStep = nextStep.next){
            for (EntityListMap<K, V> downStep = nextStep; downStep != null ; downStep = downStep.down){
                stringBuilder.append(downStep).append(';').append('\n');
            }
        }
        return stringBuilder.toString();
    }

    private int hash(K key) {
        int h;
        return (h = key.hashCode()) ^ (h >>> 16) & 32;
    }
    public static class EntityListMap<K, V> implements Entity<K, V>{
        private K key;
        private V value;
        private int hash;
        EntityListMap<K, V> next;
        EntityListMap<K, V> prev;
        EntityListMap<K, V> down;

        public EntityListMap(K key, V value) {
            if (key == null || value == null){
                throw new IllegalArgumentException();
            }
            this.key = key;
            this.value = value;
        }

        public int getHash() {
            return hash;
        }

        public void setHash(int hash) {
            this.hash = hash;
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
