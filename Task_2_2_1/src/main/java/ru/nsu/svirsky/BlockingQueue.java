package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.interfaces.QueueForProducer;
import ru.nsu.svirsky.interfaces.QueueForConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class BlockingQueue<T> implements QueueForConsumer<T>, QueueForProducer<T> {
    private final List<T> elements = new ArrayList<>();
    private final int capacity;
    private final Object getLock = new Object();
    private final Object putLock = new Object();
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    public BlockingQueue() {
        this(0);
    }

    public void add(T element) throws InterruptedException, QueueClosedException {
        if (isClosed.get()) {
            throw new QueueClosedException();
        }

        synchronized (putLock) {
            while (isFull()) {
                putLock.wait();
            }
            push(element);
        }

        synchronized (getLock) {
            getLock.notify();
        }
    }

    public T get() throws InterruptedException {
        T element;
        synchronized (getLock) {
            while (isEmpty()) {
                getLock.wait();
            }
            element = pop();
        }
        synchronized (putLock) {
            putLock.notify();
        }
        return element;
    }

    public T noWaitGet() {
        synchronized (getLock) {
            if (isEmpty()) {
                return null;
            } else {
                return elements.removeFirst();
            }
        }
    }

    public boolean isEmpty() {
        synchronized (elements) {
            return elements.isEmpty();
        }
    }

    private boolean isFull() {
        synchronized (elements) {
            return capacity != 0 && elements.size() >= capacity;
        }
    }

    private T pop() {
        synchronized (elements) {
            return elements.removeFirst();
        }
    }

    private void push(T element) {
        synchronized (elements) {
            elements.add(element);
        }
    }

    public void close() {
        isClosed.set(true);
    }
}
