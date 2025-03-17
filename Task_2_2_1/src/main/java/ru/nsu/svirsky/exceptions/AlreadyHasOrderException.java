package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.Client;

public class AlreadyHasOrderException extends Exception {
    public AlreadyHasOrderException(Client client) {
        super(String.format("%s already has order!", client));
    }
}
