package ru.nsu.svirsky;

import java.util.ArrayList;
import java.util.List;

public class PizzaStorage {
    private final List<Pizza> pizzas = new ArrayList<>();
    private final int capacity;
    private final Object getPizzaLock = new Object();
    private final Object putPizzaLock = new Object();

    public PizzaStorage(int capacity) {
        this.capacity = capacity;
    }

    public void put(Pizza pizza) throws InterruptedException {
        synchronized (putPizzaLock) {
            while (isStorageFull()) {
                putPizzaLock.wait();
            }
            push(pizza);
            synchronized (getPizzaLock) {
                getPizzaLock.notify();
            }
        }
    }

    public Pizza get() throws InterruptedException {
        synchronized (getPizzaLock) {
            while (isEmpty()) {
                getPizzaLock.wait();
            }
            Pizza pizza = pop();
            synchronized (putPizzaLock) {
                putPizzaLock.notify();
            }
            return pizza;
        }
    }

    public Pizza noWaitGet() {
        synchronized (getPizzaLock) {
            if (pizzas.isEmpty()) {
                return null;
            } else {
                return pizzas.removeFirst();
            }
        }
    }

    public boolean isEmpty() {
        synchronized (pizzas) {
            return pizzas.isEmpty();
        }
    }

    private boolean isStorageFull() {
        synchronized (pizzas) {
            return pizzas.size() >= capacity;
        }
    }

    private Pizza pop() {
        synchronized (pizzas) {
            return pizzas.removeFirst();
        }
    }

    private void push(Pizza pizza) {
        synchronized (pizzas) {
            pizzas.add(pizza);
        }
    }
}
