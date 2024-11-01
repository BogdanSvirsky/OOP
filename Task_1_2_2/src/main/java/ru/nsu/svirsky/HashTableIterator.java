package ru.nsu.svirsky;

import java.util.Iterator;
import java.util.function.Function;

import javax.lang.model.type.NullType;

public class HashTableIterator<K, V> implements Iterator<Entry<K, V>> {
    private int currentHash;
    private Iterator<Entry<K, V>> bucketIterator;
    private Function<NullType, Integer> getSize;
    private Function<Integer, Iterator<Entry<K, V>>> getBucketIterator;

    public HashTableIterator(
            Function<NullType, Integer> getSize,
            Function<Integer, Iterator<Entry<K, V>>> getBucketIterator) {
        currentHash = 0;
        this.getSize = getSize;
        this.getBucketIterator = getBucketIterator;
        bucketIterator = getBucketIterator.apply(currentHash);
    }

    @Override
    public boolean hasNext() {
        return currentHash < getSize.apply(null) || bucketIterator.hasNext();
    }

    @Override
    public Entry<K, V> next() {
        if (hasNext()) {
            Entry<K, V> result = bucketIterator.next();
            
            while (!bucketIterator.hasNext() && currentHash < getSize.apply(null)) {
                bucketIterator = getBucketIterator.apply(++currentHash);
            }

            return result;
        } else {
            return null;
        }
    }
}
