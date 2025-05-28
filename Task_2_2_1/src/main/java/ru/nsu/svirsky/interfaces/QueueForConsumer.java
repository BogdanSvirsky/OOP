package ru.nsu.svirsky.interfaces;

/**
 * Interface for a queue that supports consumer operations.
 *
 * @param <T> The type of elements in the queue.
 * @author BogdanSvirsky
 */
public interface QueueForConsumer<T> {
    /**
     * Retrieves and removes an element from the queue, waiting if necessary.
     *
     * @return The retrieved element.
     * @throws InterruptedException If the thread is interrupted while waiting.
     */
    T get() throws InterruptedException;

    /**
     * Retrieves and removes an element from the queue without waiting.
     *
     * @return The retrieved element, or null if the queue is empty.
     */
    T noWaitGet();

    /**
     * Checks if the queue is empty.
     *
     * @return True if the queue is empty, false otherwise.
     */
    boolean isEmpty();
}