package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.Baker;

public class HasNoOrderQueueException extends Exception {
    public HasNoOrderQueueException(Baker baker) {
        super(baker + " has no order queue!");
    }
}
