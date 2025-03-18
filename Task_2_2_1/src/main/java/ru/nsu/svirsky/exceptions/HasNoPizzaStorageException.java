package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.pizzeria.Baker;

/**
 * Thrown when a baker attempts to work without an assigned pizza storage.
 *
 * @author BogdanSvirsky
 */
public class HasNoPizzaStorageException extends Exception {
    /**
     * Creates an exception with a message indicating the baker has no pizza storage.
     *
     * @param baker The baker without a pizza storage.
     */
    public HasNoPizzaStorageException(Baker baker) {
        super(baker + " has no pizza storage!");
    }
}