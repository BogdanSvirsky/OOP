package ru.nsu.svirsky;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;
import java.util.function.Function;

@SuppressWarnings("unchecked")
public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private int size;
    private int elementsCount;
    private LinkedList<Entry<K, V>>[] buckets;

    public HashTable() {
        size = 1;
        buckets = (LinkedList<Entry<K, V>>[]) new Object[size];
        elementsCount = 0;
    }

    public void put(K key, V value) {
        elementsCount++;

        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();
        while (iterator.hasNext()) {
            if (key.equals(iterator.next().getKey())) {
                iterator.remove();
                elementsCount--;
                break;
            }
        }

        buckets[key.hashCode() % size].add(new Entry<K, V>(key, value));
        checkSize();
    }

    public void remove(K key, V value) {
        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();

        for (Entry<K, V> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()) {
            if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                iterator.remove();
                elementsCount--;
                checkSize();
                return;
            }
        }
    }

    public V get(K key) {
        for (Entry<K, V> entry : buckets[key.hashCode() % size]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        return null;
    }

    public void update(K key, V value) {
        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();

        for (Entry<K, V> entry = iterator.next(); iterator.hasNext(); entry = iterator.next()) {
            if (entry.getKey().equals(key)) {
                iterator.remove();
                buckets[key.hashCode() % size].add(new Entry<K, V>(key, value));
                return;
            }
        }
    }

    public boolean contains(K key) {
        for (Entry<K, V> entry : buckets[key.hashCode() % size]) {
            if (entry.getKey().equals(key)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new HashTableIterator<K, V>(smth -> size, hash -> buckets[hash % size].iterator());
    }

    private void checkSize() {
        if (elementsCount > size) {
            size *= 2;
            LinkedList<Entry<K, V>>[] newBuckets = (LinkedList<Entry<K, V>>[]) new Object[size];
            for (LinkedList<Entry<K, V>> linkedList : buckets) {
                for (Entry<K, V> entry : linkedList) {
                    newBuckets[entry.key.hashCode() % size].add(entry);
                }
            }

            buckets = newBuckets;
        }
    }
}
