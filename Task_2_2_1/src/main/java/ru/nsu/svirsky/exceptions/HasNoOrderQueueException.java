package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.pizzeria.Baker;

/**
 * Thrown when a baker attempts to work without an assigned order queue.
 * @author BogdanSvirsky
 */
public class HasNoOrderQueueException extends Exception {
    /**
     * Creates an exception with a message indicating the baker has no order queue.
     *
     * @param baker The baker without an order queue.
     */
    public HasNoOrderQueueException(Baker baker) {
        super(baker + " has no order queue!");
    }
}