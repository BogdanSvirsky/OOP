package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeapsortTest {
    @Test
    void mainCheck() {
        Heapsort.main(null);
        assertTrue(true);
    }

    @Test
    void basicTests() {
        assertArrayEquals(new int[]{1, 2, 3}, Heapsort.heapsort(new int[]{3, 2, 1}));
        assertArrayEquals(
                new int[]{-2323232, 90, 100, 2131231233},
                Heapsort.heapsort(new int[]{100, -2323232, 2131231233, 90})
        );
        assertArrayEquals(new int[] {}, Heapsort.heapsort(new int[] {}));
        assertArrayEquals(new int[] {-5, 10, 10, 12, 27}, Heapsort.heapsort(new int[] {-5, 12, 10, 10, 27}));
        assertArrayEquals(
                new int[] {Integer.MIN_VALUE, Integer.MAX_VALUE, Integer.MAX_VALUE},
                Heapsort.heapsort(new int[] {Integer.MAX_VALUE, Integer.MIN_VALUE, Integer.MAX_VALUE})
        );
    }

}