package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.Pizza;
import ru.nsu.svirsky.exceptions.BackpackIsFullException;

public interface ToBackpackAdder {
    void add(Pizza pizza) throws BackpackIsFullException;
}
