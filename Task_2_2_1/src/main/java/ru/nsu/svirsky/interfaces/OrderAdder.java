package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.PizzaOrder;
import ru.nsu.svirsky.exceptions.OrderQueueClosedException;

public interface OrderAdder {
    void add(PizzaOrder order) throws OrderQueueClosedException;
}
