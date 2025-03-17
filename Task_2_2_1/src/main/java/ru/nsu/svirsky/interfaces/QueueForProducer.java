package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.exceptions.QueueClosedException;

public interface QueueForProducer<T> {
    void add(T element) throws InterruptedException, QueueClosedException;
}
