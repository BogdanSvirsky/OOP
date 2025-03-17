package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.QueueForProducer;

public class Client<IdType> {
    private final IdType id;
    private PizzaOrder currentOrder;
    private final Object orderLock = new Object();
    private final QueueForProducer<PizzaOrder> orderQueue;
    private final Thread executor = new Thread(this::waitOrder);

    public Client(IdGetter<IdType> idGetter, QueueForProducer<PizzaOrder> orderQueue) {
        this.id = idGetter.get();
        this.orderQueue = orderQueue;
    }

    public PizzaOrder makeAnOrder(IdGetter<IdType> orderIdGetter, String pizzaName)
            throws AlreadyHasOrderException, QueueClosedException, InterruptedException {
        if (currentOrder != null) {
            throw new AlreadyHasOrderException(this);
        }

        currentOrder = new PizzaOrder<>(orderIdGetter, pizzaName, this::getAnOrder);

        orderQueue.add(currentOrder);

        executor.start();

        return currentOrder;
    }

    private void getAnOrder(Pizza pizza) {
        synchronized (orderLock) {
            if (currentOrder.equals(pizza.order)) {
                currentOrder.complete();
            } else {
                currentOrder.invalidRecipient();
            }
            orderLock.notify();
        }
    }

    private void waitOrder() {
        synchronized (orderLock) {
            while (!currentOrder.isCompleted()) {
                try {
                    orderLock.wait();
                } catch (InterruptedException e) {
                    System.err.printf("%s order waiting was interrupted!\n", this);
                    return;
                }
            }
        }
        System.out.printf("%s received %s\n", this, currentOrder);
    }

    @Override
    public String toString() {
        return String.format("Client{id: %s}", id);
    }

    public String status() {
        return executor.getState().name();
    }
}
