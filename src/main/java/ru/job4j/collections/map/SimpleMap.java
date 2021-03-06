package ru.job4j.collections.map;

import java.util.*;

public class SimpleMap<K, V> implements MyMap<K, V> {

    private static final float LOAD_FACTOR = 0.75f;
    private int capacity = 8;
    private int count = 0;
    private int size = 0;
    private MapEntry<K, V>[] table = new MapEntry[capacity];

    private static class MapEntry<K, V> {

        K key;
        V value;

        public MapEntry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public V getValue() {
            return value;
        }

        public K getKey() {
            return key;
        }



        @Override
        public String toString() {
            return "MapEntry{" + "key=" + key + ", value=" + value + '}';
        }
    }

    private int hash(K key) {
        return Math.abs(key.hashCode() % table.length);
    }

    private int indexFor(int hash) {
        return hash & (table.length - 1);
    }


    private void expand() {
        MapEntry<K, V>[] temp = table;
        this.capacity *= 2;
        table = (MapEntry<K, V>[]) new MapEntry[capacity];
        for (MapEntry<K, V> node : temp) {
            if (node != null) {
                K key = node.key;
                V value = node.value;
                int hash = hash(key);
                int index = indexFor(hash);
                table[index] = new MapEntry<>(key, value);
            }
        }
    }

    @Override
    public boolean put(K key, V value) {
        boolean result = false;
        if (table.length * LOAD_FACTOR == size) {
            expand();
        }
        int hash = hash(key);
        int index = indexFor(hash);
        MapEntry<K, V> current = table[index];
        if (current == null) {
            table[index] = new MapEntry<>(key, value);

            result = true;
            count++;
            size++;
        }
        return result;
    }

    @Override
    public V get(K key) {
        int hash = hash(key);
        int index = indexFor(hash);
        MapEntry<K, V> current = table[index];
        V value = null;
        if (current != null && key.equals(current.getKey())) {
            value = current.value;
        }
        return value;
    }

    @Override
    public boolean remove(K key) {
        boolean result = false;
        int hash = hash(key);
        int index = indexFor(hash);
        MapEntry<K, V> current = table[index];
        if (current != null && key.equals(current.getKey())) {
            table[index] = null;
            result = true;
            size--;
            count++;
        }
        return result;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("-----------------------\n");
        for (int i = 0; i < table.length; i++) {
            sb.append(String.format("%d = [%s]%n", i, table[i]));
        }
        sb.append("-----------------------\n");
        return sb.toString();
    }

    @Override
    public Iterator<K> iterator() {
        return new Iterator<K>() {

            private int pointer;
            private final int modCount = count;

            @Override
            public boolean hasNext() {
                if (modCount != count) {
                    throw new ConcurrentModificationException();
                }
                while (pointer < table.length && table[pointer] == null) {
                    pointer++;
                }
                return pointer < table.length;
            }

            @Override
            public K next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return table[pointer++].getKey();
            }
        };
    }

}
