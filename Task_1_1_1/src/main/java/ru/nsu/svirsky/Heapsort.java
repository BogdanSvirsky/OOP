package ru.nsu.svirsky;

class Heap {
    int[] elements;
    int size = 0;

    public Heap(int[] array) {
        elements = array.clone();
        for (int i = 0; i < array.length; i++) {
            size++;
            siftUp(i);
        }
    }

    int extractMin() {
        int result = elements[0];
        elements[0] = elements[--size];
        siftDown(0);
        return result;
    }

    private void siftDown(int index) {
        int minChild;
        while (2 * index + 1 < size) {
            minChild = 2 * index + 1;

            if (2 * index + 2 < size && elements[2 * index + 2] < elements[minChild])
                minChild = 2 * index + 2;

            if (elements[index] > elements[minChild]) {
                swap(index, minChild);
                index = minChild;
            } else
                break;
        }
    }

    private void siftUp(int index) {
        while (index != 0) {
            if (elements[index] < elements[(index - 1) / 2]) {
                swap(index, (index - 1) / 2);
                index = (index - 1) / 2;
            } else
                break;
        }
    }

    private void swap(int index1, int index2) {
        int tmp = elements[index1];
        elements[index1] = elements[index2];
        elements[index2] = tmp;
    }
}

public class Heapsort {
    public static void main(String[] args) {
        System.out.println("main works");
    }

    public static int[] heapsort(int[] array) {
        int[] result = array.clone();
        Heap heap = new Heap(array);

        for (int i = 0; i < array.length; i++)
            result[i] = heap.extractMin();

        return result;
    }

}
