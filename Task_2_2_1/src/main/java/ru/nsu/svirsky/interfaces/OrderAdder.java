package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.PizzaOrder;
import ru.nsu.svirsky.exceptions.QueueClosedException;

public interface OrderAdder {
    void makeAnOrder(PizzaOrder order) throws QueueClosedException, InterruptedException;
}
