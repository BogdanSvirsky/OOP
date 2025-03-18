package ru.nsu.svirsky;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.interfaces.QueueForConsumer;
import ru.nsu.svirsky.interfaces.QueueForProducer;

/**
 * A thread-safe blocking queue implementation that supports producer-consumer operations.
 *
 * @param <T> The type of elements stored in the queue.
 * @author BogdanSvirsky
 */
public class BlockingQueue<T> implements QueueForConsumer<T>, QueueForProducer<T> {
    private final List<T> elements = new ArrayList<>();
    private final int capacity;
    private final Object getLock = new Object();
    private final Object putLock = new Object();
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    /**
     * Constructs a blocking queue with the specified capacity.
     *
     * @param capacity The maximum capacity of the queue. If 0, the queue is unbounded.
     */
    public BlockingQueue(int capacity) {
        this.capacity = capacity;
    }

    /**
     * Constructs an unbounded blocking queue.
     */
    public BlockingQueue() {
        this(0);
    }

    /**
     * Adds an element to the queue. Blocks if the queue is full.
     *
     * @param element The element to add.
     * @throws InterruptedException If the thread is interrupted while waiting.
     * @throws QueueClosedException If the queue is closed.
     */
    @Override
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

    /**
     * Retrieves and removes an element from the queue. Blocks if the queue is empty.
     *
     * @return The retrieved element.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    @Override
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

    /**
     * Retrieves and removes an element from the queue without blocking.
     *
     * @return The retrieved element, or null if the queue is empty.
     */
    @Override
    public T noWaitGet() {
        synchronized (getLock) {
            if (isEmpty()) {
                return null;
            } else {
                return elements.removeFirst();
            }
        }
    }

    /**
     * Checks if the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    @Override
    public boolean isEmpty() {
        synchronized (elements) {
            return elements.isEmpty();
        }
    }

    /**
     * Checks if the queue is full.
     *
     * @return True if the queue is full, false otherwise.
     */
    private boolean isFull() {
        synchronized (elements) {
            return capacity != 0 && elements.size() >= capacity;
        }
    }

    /**
     * Removes and returns the first element in the queue.
     *
     * @return The first element in the queue.
     */
    private T pop() {
        synchronized (elements) {
            return elements.removeFirst();
        }
    }

    /**
     * Adds an element to the end of the queue.
     *
     * @param element The element to add.
     */
    private void push(T element) {
        synchronized (elements) {
            elements.add(element);
        }
    }

    /**
     * Closes the queue, preventing further additions.
     */
    public void close() {
        isClosed.set(true);
    }
}