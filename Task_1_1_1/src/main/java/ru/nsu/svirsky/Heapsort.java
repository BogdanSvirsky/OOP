package ru.nsu.svirsky;

/**
 * Implementation of heapsort.
 * 
 * @author Bogdan Svirsky
 */
public class Heapsort {
    /**
     * Simple test of heapsort function.
     * 
     * @param args doesn't matter
     */
    public static void main(String[] args) {
        int[] inputArray = new int[] { 100, -2323, 58585, 49, 563, 823 };
        System.out.print("Integer array: ");
        for (int element : inputArray) {
            System.out.print(element + " ");
        }
        System.out.print("\nArray after heapsort: ");
        for (int element : heapsort(inputArray)) {
            System.out.print(element + " ");
        }

    }

    /**
     * Generates heap from array and builds result from it.
     * 
     * @param array input array
     * @return sorted array
     */
    public static int[] heapsort(int[] array) {
        int[] result = array.clone();
        Heap heap = new Heap(array);

        for (int i = 0; i < array.length; i++) {
            result[i] = heap.extractMin();
        }

        return result;
    }

}
