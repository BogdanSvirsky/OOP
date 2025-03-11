package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.OrderQueueClosedException;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class OrderQueue {
    private final List<PizzaOrder> orders = new ArrayList<>();
    private final AtomicBoolean isClosed = new AtomicBoolean(false);

    public void add(PizzaOrder order) throws OrderQueueClosedException {
        if (isClosed.get()) {
            throw new OrderQueueClosedException();
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
