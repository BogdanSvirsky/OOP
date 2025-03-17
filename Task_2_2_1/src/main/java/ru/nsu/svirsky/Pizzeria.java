package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.HasNoOrderQueueException;
import ru.nsu.svirsky.exceptions.HasNoPizzaStorageException;
import ru.nsu.svirsky.interfaces.QueueForProducer;

import java.util.ArrayList;
import java.util.List;

public class Pizzeria {
    private final BlockingQueue<PizzaOrder> orderQueue;
    private final BlockingQueue<Pizza> pizzaStorage;
    private final List<Baker> bakers;
    private final List<Courier> couriers;

    public Pizzeria(int pizzaStorageCapacity) {
        orderQueue = new BlockingQueue<>();
        pizzaStorage = new BlockingQueue<>(pizzaStorageCapacity);
        bakers = new ArrayList<>();
        couriers = new ArrayList<>();
    }

    public Pizzeria(List<Baker> bakers, List<Courier> couriers, int pizzaStorageCapacity) {
        this(pizzaStorageCapacity);
        this.bakers.addAll(bakers);
        this.couriers.addAll(couriers);

        for (Baker baker : this.bakers) {
            baker.setOrderQueue(orderQueue);
            baker.setPizzaStorage(pizzaStorage);
        }

        for (Courier courier : this.couriers) {
            courier.setStorage(pizzaStorage);
        }
    }

    public void beginWork() {
        for (Baker baker : bakers) {
            try {
                baker.beginWork();
            } catch (HasNoOrderQueueException | HasNoPizzaStorageException e) {
                throw new RuntimeException(e);
            }
        }

        for (Courier courier : couriers) {
            courier.beginWork();
        }
    }

    public void finishWork() {
        new Thread(() -> {
            orderQueue.close();

            for (Baker baker : bakers) {
                baker.finishWork();
            }
            for (Baker baker : bakers) {
                try {
                    baker.waitExecution();
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }

            for (Courier courier : couriers) {
                courier.finishWork();
            }
        }).start();
    }

    public void waitAll() throws InterruptedException {
        for (Baker baker : bakers) {
            baker.waitExecution();
        }

        for (Courier courier : couriers) {
            courier.waitExecution();
        }
    }

    public QueueForProducer<PizzaOrder> getQueueForProducer() {
        return orderQueue;
    }
}
