package ru.nsu.svirsky.pizzeria;

import ru.nsu.svirsky.BlockingQueue;
import ru.nsu.svirsky.config.PizzeriaConfig;
import ru.nsu.svirsky.exceptions.HasNoOrderQueueException;
import ru.nsu.svirsky.exceptions.HasNoPizzaStorageException;
import ru.nsu.svirsky.interfaces.QueueForProducer;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a pizzeria that manages bakers, couriers, and pizza orders.
 *
 * @author BogdanSvirsky
 */
public class Pizzeria {
    private final BlockingQueue<PizzaOrder> orderQueue;
    private final BlockingQueue<Pizza> pizzaStorage;
    private final List<Baker> bakers;
    private final List<Courier> couriers;

    /**
     * Constructs a new pizzeria with the specified pizza storage capacity.
     *
     * @param pizzaStorageCapacity The maximum capacity of the pizza storage.
     */
    public Pizzeria(int pizzaStorageCapacity) {
        orderQueue = new BlockingQueue<>();
        pizzaStorage = new BlockingQueue<>(pizzaStorageCapacity);
        bakers = new ArrayList<>();
        couriers = new ArrayList<>();
    }

    /**
     * Constructs a new pizzeria with the specified bakers, couriers, and pizza storage capacity.
     *
     * @param bakers               The list of bakers.
     * @param couriers             The list of couriers.
     * @param pizzaStorageCapacity The maximum capacity of the pizza storage.
     */
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

    /**
     * Constructs a new pizzeria using the provided configuration.
     *
     * @param config The pizzeria configuration.
     */
    public Pizzeria(PizzeriaConfig config) {
        this(config.getBakers(), config.getCouriers(), config.getPizzaStorageCapacity());
    }

    /**
     * Starts the work of all bakers and couriers in the pizzeria.
     */
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

    /**
     * Stops the work of all bakers and couriers in the pizzeria.
     */
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

    /**
     * Waits for all bakers and couriers to finish their tasks.
     *
     * @throws InterruptedException If the thread is interrupted.
     */
    public void waitAll() throws InterruptedException {
        for (Baker baker : bakers) {
            baker.waitExecution();
        }

        for (Courier courier : couriers) {
            courier.waitExecution();
        }
    }

    /**
     * Retrieves the order queue for producers.
     *
     * @return The order queue.
     */
    public QueueForProducer<PizzaOrder> getQueueForProducer() {
        return orderQueue;
    }
}