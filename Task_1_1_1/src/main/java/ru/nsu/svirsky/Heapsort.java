package ru.nsu.svirsky;

/**
 * Implementation of heapsort
 * 
 * @author Bogdan Svirsky
 */
public class Heapsort {

    /**
     * Simple test of heapsort function
     * 
     * @param args doesn't matter
     */
    public static void main(String[] args) {
        for (int elem : heapsort(new int[] { 5, 1, 4, 2, 3 })) {
            System.out.print(elem + " ");
        }
        System.out.println();
    }

    /**
     * Generates heap from array and builds result from it
     * 
     * @param array input array
     * @return sorted array
     */
    public static int[] heapsort(int[] array) {
        int[] result = array.clone();
        Heap heap = new Heap(array);

        for (int i = 0; i < array.length; i++)
            result[i] = heap.extractMin();

        return result;
    }

}
