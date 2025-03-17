package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.PizzaOrder;

public interface OrderQueueForBaker {
    PizzaOrder get() throws InterruptedException;

    PizzaOrder noWaitGet();
}
