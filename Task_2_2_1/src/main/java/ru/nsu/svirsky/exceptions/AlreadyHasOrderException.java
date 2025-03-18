package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.pizzeria.Client;

/**
 * Thrown when a client attempts to place an order while already having an active order.
 * @author BogdanSvirsky
 */
public class AlreadyHasOrderException extends Exception {
    /**
     * Creates an exception with a message indicating the client already has an order.
     *
     * @param client The client who already has an order.
     */
    public AlreadyHasOrderException(Client client) {
        super(String.format("%s already has order!", client));
    }
}