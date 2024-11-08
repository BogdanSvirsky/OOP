package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ConcurrentModificationException;
import java.util.HashSet;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import org.junit.jupiter.api.Test;

public class HashTableTest {
    @Test
    void putTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<Integer, Integer>();
        Set<Entry<Integer, Integer>> entries = generateHashTable(hashTable, 100);

        for (Entry<Integer, Integer> entry : entries) {
            hashTable.put(entry.getKey(), entry.getValue());
        }

        for (Entry<Integer, Integer> entry : entries) {
            assertTrue(hashTable.contains(entry.getKey(), entry.getValue()));
            hashTable.remove(entry.getKey(), entry.getValue());
        }

        for (Entry<Integer, Integer> entry : entries) {
            assertFalse(hashTable.contains(entry.getKey(), entry.getValue()));
        }
    }

    @Test
    void getTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<>();

        assertThrows(NoSuchElementException.class, () -> hashTable.get(0));

        Set<Entry<Integer, Integer>> entries = generateHashTable(hashTable, 100);

        for (Entry<Integer, Integer> entry : entries) {
            assertEquals(hashTable.get(entry.getKey()), entry.getValue());
        }
    }

    @Test
    void updateTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<>();
        Set<Entry<Integer, Integer>> entries = generateHashTable(hashTable, 100);

        for (Entry<Integer, Integer> entry : entries) {
            hashTable.update(entry.getKey(), entry.getValue() + 10);
        }

        for (Entry<Integer, Integer> entry : entries) {
            assertTrue(hashTable.contains(entry.getKey(), entry.getValue() + 10));
        }
    }

    @Test
    void iteratorTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<Integer, Integer>();
        Set<Entry<Integer, Integer>> entries = generateHashTable(hashTable, 10);

        for (Entry<Integer, Integer> entry : entries) {
            hashTable.put(entry.getKey(), entry.getValue());
        }

        HashSet<Entry<Integer, Integer>> hashTableEntries = new HashSet<>();

        for (Entry<Integer, Integer> entry : hashTable) {
            hashTableEntries.add(entry);
        }

        assertEquals(entries, hashTableEntries);

        Iterator<Entry<Integer, Integer>> iterator = hashTable.iterator();

        while (iterator.hasNext()) {
            iterator.next();
        }

        assertThrows(NoSuchElementException.class, () -> iterator.next());
    }

    @Test
    void concurrentModificationTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<>();
        hashTable.put(1, 1321);
        hashTable.put(2, 949494);
        hashTable.put(3, 90090);

        Iterator<Entry<Integer, Integer>> iterator1 = hashTable.iterator();
        iterator1.next();
        hashTable.put(4, 2323232);

        assertThrows(ConcurrentModificationException.class, () -> iterator1.next());
        assertThrows(ConcurrentModificationException.class, () -> iterator1.hasNext());

        Iterator<Entry<Integer, Integer>> iterator2 = hashTable.iterator();
        iterator2.next();
        hashTable.update(1, 20);

        assertThrows(ConcurrentModificationException.class, () -> iterator2.next());
        assertThrows(ConcurrentModificationException.class, () -> iterator2.hasNext());

        Iterator<Entry<Integer, Integer>> iterator3 = hashTable.iterator();
        iterator3.next();
        hashTable.remove(1, 20);

        assertThrows(ConcurrentModificationException.class, () -> iterator3.next());
        assertThrows(ConcurrentModificationException.class, () -> iterator3.hasNext());
    }

    @Test
    void toStringTest() {
        HashTable<Integer, Integer> hashTable = new HashTable<>();
        Set<Entry<Integer, Integer>> entries = generateHashTable(hashTable, 10);
        String result = hashTable.toString();
        Iterator<Entry<Integer, Integer>> iterator = entries.iterator();

        while (iterator.hasNext()) {
            assertTrue(result.contains(iterator.next().toString()));
        }
    }

    @Test
    void equalsTest() {
        HashTable<Integer, Integer> hashTable1 = new HashTable<>();
        HashTable<Integer, Integer> hashTable2 = new HashTable<>();

        hashTable1.put(1, 1);
        hashTable1.put(2, 2);
        hashTable2.put(2, 2);
        hashTable2.put(1, 1);

        assertEquals(hashTable1, hashTable2);

        hashTable1.put(3, 10);
        hashTable2.put(3, 11);

        assertNotEquals(hashTable1, hashTable2);

        hashTable1.remove(3, 10);
        hashTable1.update(1, 2);
        hashTable2.remove(3, 11);
        hashTable2.update(2, 1);

        assertNotEquals(hashTable1, hashTable2);
    }

    Set<Entry<Integer, Integer>> generateHashTable(
            HashTable<Integer, Integer> hashTable,
            int elementsCount) {
        HashSet<Entry<Integer, Integer>> entries = new HashSet<>();
        Random random = new Random();
        int key;
        int value;
        for (int i = 0; i < elementsCount; i++) {
            key = random.nextInt(Integer.MAX_VALUE);
            value = random.nextInt(Integer.MAX_VALUE);
            entries.add(new Entry<Integer, Integer>(key, value));
            hashTable.put(key, value);
        }

        return entries;
    }
}
