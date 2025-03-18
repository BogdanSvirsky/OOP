package ru.nsu.svirsky.pizzeria;

import ru.nsu.svirsky.exceptions.AlreadyHasOrderException;
import ru.nsu.svirsky.exceptions.QueueClosedException;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.QueueForProducer;

/**
 * Represents a client who places pizza orders and waits for their completion.
 *
 * @param <T> The type of the client's ID.
 * @author BogdanSvirsky
 */
public class Client<T> {
    private final T id;
    private final Object orderLock = new Object();
    private final QueueForProducer<PizzaOrder> orderQueue;
    private PizzaOrder currentOrder;
    private final Thread executor = new Thread(this::waitOrder);

    /**
     * Constructs a new client.
     *
     * @param idGetter   Provides the client's ID.
     * @param orderQueue The queue where orders are placed.
     */
    public Client(IdGetter<T> idGetter, QueueForProducer<PizzaOrder> orderQueue) {
        this.id = idGetter.get();
        this.orderQueue = orderQueue;
    }

    /**
     * Places a new pizza order.
     *
     * @param orderIdGetter Provides the order ID.
     * @param pizzaName     The name of the pizza.
     * @return The created pizza order.
     * @throws AlreadyHasOrderException If the client already has an active order.
     * @throws QueueClosedException     If the order queue is closed.
     * @throws InterruptedException     If the thread is interrupted.
     */
    public PizzaOrder makeAnOrder(IdGetter<?> orderIdGetter, String pizzaName)
            throws AlreadyHasOrderException, QueueClosedException, InterruptedException {
        if (currentOrder != null) {
            throw new AlreadyHasOrderException(this);
        }

        currentOrder = new PizzaOrder<>(orderIdGetter, pizzaName, this::getAnOrder);

        orderQueue.add(currentOrder);

        executor.start();

        return currentOrder;
    }

    /**
     * Handles the received pizza.
     *
     * @param pizza The pizza to be received.
     */
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

    /**
     * Waits for the current order to be completed.
     */
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

    /**
     * Retrieves the current status of the client's thread.
     *
     * @return The status of the client's thread.
     */
    public String status() {
        return executor.getState().name();
    }
}