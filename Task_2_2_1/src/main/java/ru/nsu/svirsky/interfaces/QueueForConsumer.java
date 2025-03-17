package ru.nsu.svirsky.interfaces;

public interface QueueForConsumer<T> {
    T get() throws InterruptedException;
    T noWaitGet();
    boolean isEmpty();
}
