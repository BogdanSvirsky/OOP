package ru.nsu.svirsky;

public class Entry<K, V> {
    private K key;
    private V value;

    public Entry(K key, V value) {
        this.key = key;
        this.value = value;
    }

    public K getKey() {
        return key;
    }

    public V getValue() {
        return value;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Entry other) {
            return key.equals(other.key) && value.equals(other.value);
        }
        return false;
    }

    @Override
    public String toString() {
        return key.toString() + ": " + value.toString();
    }

    @Override
    public int hashCode() {
        return key.hashCode() + value.hashCode();
    }
}
