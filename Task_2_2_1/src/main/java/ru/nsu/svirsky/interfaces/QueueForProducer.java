package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.exceptions.QueueClosedException;

/**
 * Interface for a queue that supports producer operations.
 *
 * @param <T> The type of elements in the queue.
 * @author BogdanSvirsky
 */
public interface QueueForProducer<T> {
    /**
     * Adds an element to the queue.
     *
     * @param element The element to add.
     * @throws InterruptedException If the thread is interrupted while waiting.
     * @throws QueueClosedException If the queue is closed and cannot accept new elements.
     */
    void add(T element) throws InterruptedException, QueueClosedException;
}