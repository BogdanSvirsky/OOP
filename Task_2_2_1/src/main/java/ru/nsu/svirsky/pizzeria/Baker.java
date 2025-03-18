package ru.nsu.svirsky.pizzeria;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;
import ru.nsu.svirsky.exceptions.HasNoOrderQueueException;
import ru.nsu.svirsky.exceptions.HasNoPizzaStorageException;
import ru.nsu.svirsky.exceptions.InvalidExecutorExeception;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.QueueForConsumer;
import ru.nsu.svirsky.interfaces.QueueForProducer;

/**
 * Represents a baker in the pizzeria who processes pizza orders.
 *
 * @param <IdType> The type of the baker's ID.
 * @author BogdanSvirsky
 */
public class Baker<IdType> {
    private final IdType bakerId;
    private final long workingTimeMillis;
    private final AtomicBoolean finishWork = new AtomicBoolean(false);
    private QueueForConsumer<PizzaOrder> orderQueue;
    private QueueForProducer<Pizza> pizzaStorage;

    /**
     * Constructs a new baker.
     *
     * @param idGetter          Provides the baker's ID.
     * @param workingTimeMillis The time it takes to cook a pizza.
     */
    public Baker(IdGetter<IdType> idGetter, long workingTimeMillis) {
        this.bakerId = idGetter.get();
        this.workingTimeMillis = workingTimeMillis;
    }

    /**
     * Starts the baker's work.
     *
     * @throws HasNoOrderQueueException   If the order queue is not set.
     * @throws HasNoPizzaStorageException If the pizza storage is not set.
     */
    public void beginWork() throws HasNoOrderQueueException, HasNoPizzaStorageException {
        if (orderQueue == null) {
            throw new HasNoOrderQueueException(this);
        }
        if (pizzaStorage == null) {
            throw new HasNoPizzaStorageException(this);
        }
        executor.start();
    }    private final Thread executor = new Thread(() -> {
        try {
            runLifecycle();
        } catch (InvalidExecutorExeception e) {
            throw new RuntimeException("Courier's executor can run its code!");
        }
    });

    private void runLifecycle() throws InvalidExecutorExeception {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExeception();
        }

        System.out.printf("%s start working\n", this);
        while (!finishWork.get()) {
            PizzaOrder order;
            try {
                order = orderQueue.get();
            } catch (InterruptedException e) {
                if (!finishWork.get()) {
                    System.err.printf("%s order getting was interrupted!\n", this);
                    return;
                } else {
                    break;
                }
            }

            processOrder(order);
        }

        PizzaOrder order = orderQueue.noWaitGet();

        while (order != null) {
            processOrder(order);
            order = orderQueue.noWaitGet();
        }

        System.out.printf("%s finish working\n", this);
    }

    private void processOrder(PizzaOrder order) throws InvalidExecutorExeception {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExeception();
        }

        order.startCooking();
        System.out.printf("%s start cooking %s\n", this, order);

        try {
            Thread.sleep(workingTimeMillis);
        } catch (InterruptedException e) {
            if (!finishWork.get()) {
                System.err.printf("Cooking of %s was interrupted!\n", this);
                return;
            } else {
                try {
                    Thread.sleep(workingTimeMillis);
                } catch (InterruptedException ex) {
                    System.err.printf("Cooking of %s was interrupted!\n", this);
                    return;
                }
            }
        }

        order.finishCooking();
        System.out.printf("%s finish cooking %s\n", this, order);

        try {
            pizzaStorage.add(new Pizza(order, order.getOrderText()));
        } catch (InterruptedException e) {
            System.err.printf("%s pizza storing was interrupted\n", this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    /**
     * Stops the baker's work.
     */
    public void finishWork() {
        finishWork.set(true);
        if (executor.getState() == Thread.State.WAITING) {
            executor.interrupt();
        }
    }

    /**
     * Waits for the baker to finish all tasks.
     *
     * @throws InterruptedException If the thread is interrupted.
     */
    public void waitExecution() throws InterruptedException {
        if (executor.isAlive()) {
            executor.join();
        }
    }

    @Override
    public String toString() {
        return String.format("Baker{id: %s}", bakerId);
    }

    /**
     * Sets the order queue for the baker.
     *
     * @param orderQueue The queue from which orders are taken.
     */
    public void setOrderQueue(QueueForConsumer<PizzaOrder> orderQueue) {
        this.orderQueue = orderQueue;
    }

    /**
     * Sets the pizza storage for the baker.
     *
     * @param pizzaStorage The storage where cooked pizzas are placed.
     */
    public void setPizzaStorage(QueueForProducer<Pizza> pizzaStorage) {
        this.pizzaStorage = pizzaStorage;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Baker<?> baker = (Baker<?>) o;
        return workingTimeMillis == baker.workingTimeMillis;
    }

    @Override
    public int hashCode() {
        return Objects.hash(workingTimeMillis);
    }




}