package ru.nsu.svirsky;

import java.lang.reflect.Array;
import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

@SuppressWarnings("unchecked")
public class HashTable<K, V> implements Iterable<Entry<K, V>> {
    private int size;
    private int elementsCount;
    private LinkedList<Entry<K, V>>[] buckets;
    private boolean modified = false;

    public HashTable() {
        size = 1;
        buckets = (LinkedList<Entry<K, V>>[]) Array.newInstance(LinkedList.class, size);
        for (int i = 0; i < size; i++) {
            buckets[i] = new LinkedList<>();
        }
        elementsCount = 0;
    }

    public void put(K key, V value) {
        modified = true;
        elementsCount++;

        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (key.equals(entry.getKey())) {
                iterator.remove();
                elementsCount--;
                break;
            }
        }

        buckets[key.hashCode() % size].add(new Entry<K, V>(key, value));
        checkSize();
    }

    public void remove(K key, V value) {
        modified = true;
        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();
        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                iterator.remove();
                elementsCount--;
                checkSize();
                return;
            }
        }
    }

    public V get(K key) throws NoSuchElementException {
        for (Entry<K, V> entry : buckets[key.hashCode() % size]) {
            if (entry.getKey().equals(key)) {
                return entry.getValue();
            }
        }

        throw new NoSuchElementException();
    }

    public void update(K key, V value) {
        modified = true;
        Iterator<Entry<K, V>> iterator = buckets[key.hashCode() % size].iterator();

        while (iterator.hasNext()) {
            Entry<K, V> entry = iterator.next();
            if (entry.getKey().equals(key)) {
                iterator.remove();
                buckets[key.hashCode() % size].add(new Entry<K, V>(key, value));
                return;
            }
        }
    }

    public boolean contains(K key, V value) {
        for (Entry<K, V> entry : buckets[key.hashCode() % size]) {
            if (entry.getKey().equals(key) && entry.getValue().equals(value)) {
                return true;
            }
        }

        return false;
    }

    @Override
    public Iterator<Entry<K, V>> iterator() {
        return new Iterator<Entry<K, V>>() {
            private int currentHash = 0;
            private Iterator<Entry<K, V>> bucketIterator = buckets[currentHash].iterator();

            {
                modified = false;
            }

            @Override
            public boolean hasNext() {
                if (modified) {
                    throw new ConcurrentModificationException();
                }

                if (bucketIterator.hasNext()) {
                    return true;
                } else {
                    do {
                        currentHash++;
                    } while (currentHash < size && buckets[currentHash].isEmpty());

                    if (currentHash >= size || buckets[currentHash].isEmpty()) {
                        return false;
                    } else {
                        bucketIterator = buckets[currentHash].iterator();
                        return true;
                    }
                }
            }

            @Override
            public Entry<K, V> next() throws NoSuchElementException {
                if (hasNext()) {
                    return bucketIterator.next();
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    private void checkSize() {
        if (elementsCount > size) {
            size *= 2;
            LinkedList<Entry<K, V>>[] newBuckets = (LinkedList<Entry<K, V>>[]) Array.newInstance(LinkedList.class,
                    size);

            for (int i = 0; i < size; i++) {
                newBuckets[i] = new LinkedList<>();
            }

            for (LinkedList<Entry<K, V>> linkedList : buckets) {
                for (Entry<K, V> entry : linkedList) {
                    newBuckets[entry.getKey().hashCode() % size].add(entry);
                }
            }

            buckets = newBuckets;
        }
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof HashTable other) {
            if (this.elementsCount == other.elementsCount) {
                HashSet entries = new HashSet<>();
                for (Object entry : other) {
                    entries.add(entry);
                }
                for (Entry<K, V> entry : this) {
                    entries.add(entry);
                }
                return entries.size() == this.elementsCount;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        String result = "";
        Iterator<Entry<K, V>> iterator = iterator();
        while (iterator.hasNext()) {
            result += iterator.next();
            if (iterator.hasNext()) {
                result += "\n";
            }
        }
        return result;
    }
}
