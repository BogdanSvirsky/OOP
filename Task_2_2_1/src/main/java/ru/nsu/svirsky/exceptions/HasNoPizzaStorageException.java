package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.Baker;

public class HasNoPizzaStorageException extends Exception {
    public HasNoPizzaStorageException(Baker baker) {
        super(baker + " has no pizza storage!");
    }
}
