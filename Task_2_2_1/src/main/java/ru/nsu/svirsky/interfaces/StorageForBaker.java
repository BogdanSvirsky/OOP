package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.Pizza;

public interface StorageForBaker {
    void add(Pizza pizza) throws InterruptedException;
}
