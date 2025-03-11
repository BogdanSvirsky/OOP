package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.Pizza;

public interface StorageForCourier {
    Pizza get() throws InterruptedException;
    Pizza noWaitGet();
    boolean isEmpty();
}
