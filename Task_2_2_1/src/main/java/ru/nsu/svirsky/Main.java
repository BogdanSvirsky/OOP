package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.OrderQueueClosedException;
import ru.nsu.svirsky.interfaces.OrderGetter;
import ru.nsu.svirsky.interfaces.StorageForCourier;

import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.sleep;

public class Main {
    public static void main(String[] args) throws AlreadyHasOrderException, InterruptedException, OrderQueueClosedException {
        final AtomicInteger bakersCount = new AtomicInteger(0);
        final AtomicInteger couriersCount = new AtomicInteger(0);
        final AtomicInteger clientsCount = new AtomicInteger(0);
        final AtomicInteger ordersCount = new AtomicInteger(0);
        OrderQueue orderQueue = new OrderQueue();
        PizzaStorage pizzaStorage = new PizzaStorage(10);
        OrderGetter orderGetter = new OrderGetter() {
            @Override
            public PizzaOrder get() throws InterruptedException {
                return orderQueue.get();
            }

            @Override
            public PizzaOrder noWaitGet() {
                return orderQueue.noWaitGet();
            }
        };
        StorageForCourier storageForCourier = new StorageForCourier() {
            @Override
            public Pizza get() throws InterruptedException {
                return pizzaStorage.get();
            }

            @Override
            public Pizza noWaitGet() {
                return pizzaStorage.noWaitGet();
            }

            @Override
            public boolean isEmpty() {
                return pizzaStorage.isEmpty();
            }
        };
        Baker<Integer>[] bakers = new Baker[]{new Baker(() -> bakersCount.addAndGet(1),
                1000, orderGetter, pizzaStorage::put)};
        Courier<Integer>[] couriers = new Courier[]{new Courier(() -> couriersCount.addAndGet(1),
                3, storageForCourier, 1000)};
        Pizzeria pizzeria = new Pizzeria(bakers, couriers);
        Client<Integer> client = new Client<>(() -> clientsCount.addAndGet(1), orderQueue::add);
        client.makeAnOrder(() -> ordersCount.addAndGet(1), "Пицца барбекю");

        sleep(3000);
        pizzeria.finishWork();
    }

}