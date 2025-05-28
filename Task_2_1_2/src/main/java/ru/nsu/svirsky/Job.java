package ru.nsu.svirsky;

public class Job {
    public final int[] inputArray;
    private final Object lock = new Object();
    private Boolean result = null;

    public Job(int[] inputArray) {
        this.inputArray = inputArray;
    }

    public boolean getResult() throws InterruptedException {
        synchronized (lock) {
            while (result == null) lock.wait();
            return result;
        }
    }

    public void setResult(boolean result) {
        synchronized (lock) {
            this.result = result;
            lock.notify();
        }
    }
}
