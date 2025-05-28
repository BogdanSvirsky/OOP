package ru.nsu.svirsky;

public class Task {
    public enum Status {RUNNING, COMPLETED, FAILED}

    private final int[] data;
    private boolean result;
    private Status status;

    public Task(int[] data) {
        this.data = data;
        this.status = Status.RUNNING;
    }

    public int[] getData() {
        return data;
    }

    public void setResult(boolean result) {
        this.result = result;
        this.status = Status.COMPLETED;
    }

    public boolean getResult() {
        return result;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }
}