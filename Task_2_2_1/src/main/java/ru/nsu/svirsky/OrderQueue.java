package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.interfaces.OrderAdder;
import ru.nsu.svirsky.interfaces.OrderQueueForBaker;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class OrderQueue implements OrderQueueForBaker, OrderAdder {
    private final List<PizzaOrder> orders = new ArrayList<>();
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    public void makeAnOrder(PizzaOrder order) throws QueueClosedException {
        if (isClosed.get()) {
            throw new QueueClosedException();
        }
        synchronized (orders) {
            orders.add(order);
            orders.notify();
        }
    }

    public PizzaOrder get() throws InterruptedException {
        synchronized (orders) {
            while (orders.isEmpty()) {
                orders.wait();
                if (isClosed.get()) {
                    return null;
                }
            }
            return orders.removeFirst();
        }
    }

    public PizzaOrder noWaitGet() {
        synchronized (orders) {
            if (orders.isEmpty()) {
                return null;
            } else {
                return orders.removeFirst();
            }
        }
    }

    public void close() {
        isClosed.set(true);
    }
}
