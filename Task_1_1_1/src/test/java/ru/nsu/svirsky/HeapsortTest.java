package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class HeapsortTest {
    @Test
    void simpleTest() {
        int[] testArray = new int[] {1, 2, 3};
    //TODO: solve problem with Gradle version
        assertEquals(testArray, Heapsort.heapsort(testArray));
    }
  
}