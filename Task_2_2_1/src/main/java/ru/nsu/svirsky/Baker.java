package ru.nsu.svirsky;

import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.OrderGetter;
import ru.nsu.svirsky.interfaces.StorageForBaker;

import java.util.concurrent.atomic.AtomicBoolean;

public class Baker<IdType> extends Thread {
    private final IdType bakerId;
    private final long workingTimeMillis;
    private final AtomicBoolean finishWork = new AtomicBoolean(false);
    private final OrderGetter fromQueueOrderGetter;
    private final StorageForBaker toPizzaStorageAdder;

    public Baker(IdGetter<IdType> idGetter, long workingTimeMillis, OrderGetter fromQueueOrderGetter,
                 StorageForBaker toPizzaStorageAdder) {
        this.bakerId = idGetter.get();
        this.workingTimeMillis = workingTimeMillis;
        this.fromQueueOrderGetter = fromQueueOrderGetter;
        this.toPizzaStorageAdder = toPizzaStorageAdder;

        start();
    }

    @Override
    public void run() {
        System.out.printf("%s start working\n", this);
        while (!finishWork.get()) {
            PizzaOrder order;
            try {
                order = fromQueueOrderGetter.get();
            } catch (InterruptedException e) {
                if (!finishWork.get()) {
                    System.err.printf("%s order getting was interrupted!\n", this);
                }
                return;
            }

            processOrder(order);
        }

        PizzaOrder order = fromQueueOrderGetter.noWaitGet();

        while (order != null) {
            processOrder(order);
            order = fromQueueOrderGetter.noWaitGet();
        }

        System.out.printf("%s finish working\n", this);
    }

    private void processOrder(PizzaOrder order) {
        order.startCooking();
        System.out.printf("%s start cooking %s\n", this, order);

        try {
            sleep(workingTimeMillis);
        } catch (InterruptedException e) {
                System.err.printf("Cooking of %s was interrupted!\n", this);

            return;
        }

        order.finishCooking();
        System.out.printf("%s finish cooking %s\n", this, order);

        try {
            toPizzaStorageAdder.add(new Pizza(order, order.getOrderText()));
        } catch (InterruptedException e) {
            System.err.printf("%s pizza storing was interrupted\n", this);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }

    public void finishWork() {
        finishWork.set(true);
        if (this.getState() == State.WAITING) {
            interrupt();
        }
    }

    @Override
    public String toString() {
        return String.format("Baker{id: %s}", bakerId);
    }
}
