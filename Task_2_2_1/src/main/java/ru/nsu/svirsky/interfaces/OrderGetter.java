package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.PizzaOrder;

public interface OrderGetter {
    PizzaOrder get() throws InterruptedException;

    PizzaOrder noWaitGet();
}
