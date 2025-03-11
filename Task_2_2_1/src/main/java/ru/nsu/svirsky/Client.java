package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.OrderQueueClosedException;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.OrderAdder;

public class Client<IdType> extends Thread {
    private final IdType id;
    private PizzaOrder currentOrder;
    private final OrderAdder toOrderQueueAdder;

    public Client(IdGetter<IdType> idGetter, OrderAdder toOrderQueueAdder) {
        this.id = idGetter.get();
        this.toOrderQueueAdder = toOrderQueueAdder;
    }

    public void makeAnOrder(IdGetter<IdType> orderIdGetter, String pizzaName)
            throws AlreadyHasOrderException, OrderQueueClosedException {
        if (currentOrder != null) {
            throw new AlreadyHasOrderException(this);
        }

        currentOrder = new PizzaOrder<>(orderIdGetter, pizzaName, this::getAnOrder);

        toOrderQueueAdder.add(currentOrder);

        start();
    }

    private void getAnOrder(Pizza pizza) {
        synchronized (currentOrder) {
            if (currentOrder.equals(pizza.order)) {
                pizza.order.complete();
            } else {
                pizza.order.invalidRecipient();
            }
            currentOrder.notify();
        }
    }

    @Override
    public void run() {
        final PizzaOrder order = currentOrder;
        synchronized (order) {
            while (!order.isCompleted()) {
                try {
                    order.wait();
                } catch (InterruptedException e) {
                    System.err.printf("%s order waiting was interrupted!\n", this);
                    return;
                }
            }
        }
        System.out.printf("%s received %s\n", this, order);
    }

    @Override
    public String toString() {
        return String.format("Client{id: %s}", id);
    }
}
