package ru.nsu.svirsky.exceptions;

import ru.nsu.svirsky.Courier;

public class BackpackIsFullException extends Exception {
    public BackpackIsFullException(Courier courier) {
        super(String.format("%s backpack is full!", courier));
    }
}
