package ru.nsu.svirsky.pizzeria;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.svirsky.exceptions.InvalidExecutorExeception;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.QueueForConsumer;

/**
 * Represents a courier who delivers pizzas from the storage to clients.
 *
 * @param <T> The type of the courier's ID.
 * @author BogdanSvirsky
 */
public class Courier<T> {
    private final T courierId;
    private final int backpackSize;
    private final List<Pizza> currentPizzas = new ArrayList<>();
    private final int deliveringTime;
    private final AtomicBoolean finishWork = new AtomicBoolean(false);
    private QueueForConsumer<Pizza> storage;
    private final Thread executor = new Thread(() -> {
        try {
            runLifecycle();
        } catch (InvalidExecutorExeception e) {
            throw new RuntimeException("Courier's executor can run its code!");
        }
    });

    /**
     * Constructs a new courier.
     *
     * @param idGetter       Provides the courier's ID.
     * @param backpackSize   The maximum number of pizzas the courier can carry.
     * @param deliveringTime The time it takes to deliver pizzas.
     */
    public Courier(IdGetter<T> idGetter, int backpackSize, int deliveringTime) {
        this.backpackSize = backpackSize;
        this.deliveringTime = deliveringTime;
        courierId = idGetter.get();
    }

    /**
     * Sets the pizza storage for the courier.
     *
     * @param storage The storage from which pizzas are taken.
     */
    public void setStorage(QueueForConsumer<Pizza> storage) {
        this.storage = storage;
    }

    private void runLifecycle() throws InvalidExecutorExeception {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExeception();
        }
        while (!finishWork.get()) {
            fillBackpack(true);
            deliver();
        }

        fillBackpack(true);

        while (!currentPizzas.isEmpty()) {
            deliver();
            fillBackpack(false);
        }

        System.out.printf("%s finish work\n", this);
    }

    private void fillBackpack(boolean isWaiting) throws InvalidExecutorExeception {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExeception();
        }
        Pizza pizza;
        while (currentPizzas.size() < backpackSize && !storage.isEmpty()) {
            if (isWaiting) {
                try {
                    pizza = storage.get();
                } catch (InterruptedException e) {
                    System.err.printf("%s backpack filling was interrupted!", this);
                    return;
                }
                if (pizza == null) {
                    return;
                }
                currentPizzas.add(pizza);
            } else {
                pizza = storage.noWaitGet();
                if (pizza == null) {
                    System.out.printf("%s can't find pizza in storage\n", this);
                    return;
                } else {
                    currentPizzas.add(pizza);
                }
            }
        }
        System.out.printf("%s has filled backpack\n", this);
    }

    private void deliver() throws InvalidExecutorExeception {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExeception();
        }
        try {
            Thread.sleep(deliveringTime);
        } catch (InterruptedException e) {
            System.err.printf("%s delivering was interrupted\n", this);
            return;
        }
        while (!currentPizzas.isEmpty()) {
            Pizza pizza = currentPizzas.removeFirst();
            System.out.printf("%s delivered %s\n", this, pizza);
            pizza.order.pizzaRecipient.get(pizza);
        }
    }

    /**
     * Starts the courier's work.
     */
    public void beginWork() {
        finishWork.set(false);
        executor.start();
    }

    /**
     * Stops the courier's work.
     */
    public void finishWork() {
        finishWork.set(true);
    }

    @Override
    public String toString() {
        return String.format("Courier{id: %s}", courierId);
    }

    /**
     * Waits for the courier to finish all tasks.
     *
     * @throws InterruptedException If the thread is interrupted.
     */
    public void waitExecution() throws InterruptedException {
        if (executor.isAlive()) {
            executor.join();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Courier<?> courier = (Courier<?>) o;
        return backpackSize == courier.backpackSize && deliveringTime == courier.deliveringTime;
    }

    @Override
    public int hashCode() {
        return Objects.hash(backpackSize, deliveringTime);
    }


}