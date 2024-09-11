package ru.nsu.svirsky;

/**
 * Class Heap is used for implementation of heapsort for array of integers.
 * 
 * @author Bogdan Svirsky
 */

public class Heap {
    private final int[] elements;
    private int size = 0;

    /**
     * Class constructor by array of integers which will be contained in a heap.
     * 
     * @param array array of integers
     */
    public Heap(int[] array) {
        elements = array.clone();
        for (int i = 0; i < array.length; i++) {
            size++;
            siftUp(i);
        }
    }

    /**
     * Deletes minimal integer from the heap and updates it.
     * 
     * @return integer
     */
    int extractMin() {
        int result = elements[0];
        elements[0] = elements[--size];
        siftDown(0);
        return result;
    }

    /**
     * Sifts integer on the index down through the heap.
     * 
     * @param index position in the elements array
     */
    private void siftDown(int index) {
        int minChild;
        while (2 * index + 1 < size) {
            minChild = 2 * index + 1;

            if (2 * index + 2 < size && elements[2 * index + 2] < elements[minChild]) {
                minChild = 2 * index + 2;
            }

            if (elements[index] > elements[minChild]) {
                swap(index, minChild);
                index = minChild;
            } else {
                break;
            }
        }
    }

    /**
     * Sifts integer on the index up through the heap.
     * 
     * @param index position in the elements array
     */
    private void siftUp(int index) {
        while (index != 0) {
            if (elements[index] < elements[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            } else {
                break;
            }
        }
    }

    /**
     * Swaps two integers in the elements array.
     * 
     * @param index1 position if the first integer in the elements array
     * @param index2 position if the second integer in the elements array
     */
    private void swap(int index1, int index2) {
        int tmp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = tmp;
    }
}
