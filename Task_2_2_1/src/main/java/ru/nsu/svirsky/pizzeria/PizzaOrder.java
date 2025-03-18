package ru.nsu.svirsky.pizzeria;

import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.PizzaRecipient;

/**
 * Represents the status of a pizza order.
 */
enum PizzaOrderStatus {
    CREATED, COOKING, COOKED, COMPLETED
}

/**
 * Represents a pizza order with its current status and recipient.
 *
 * @param <IdType> The type of the order's ID.
 * @author BogdanSvirsky
 */
public class PizzaOrder<IdType> {
    public final PizzaRecipient pizzaRecipient;
    private final IdType id;
    private final String orderText;
    private PizzaOrderStatus status = PizzaOrderStatus.CREATED;

    /**
     * Constructs a new pizza order.
     *
     * @param idGetter       Provides the order ID.
     * @param orderText      The description of the order.
     * @param pizzaRecipient The recipient of the pizza.
     */
    public PizzaOrder(IdGetter<IdType> idGetter, String orderText, PizzaRecipient pizzaRecipient) {
        this.id = idGetter.get();
        this.orderText = orderText;
        this.pizzaRecipient = pizzaRecipient;
        System.out.printf("%s CREATED\n", this);
    }

    /**
     * Marks the order as being cooked.
     */
    public void startCooking() {
        status = PizzaOrderStatus.COOKING;
        System.out.printf("%s COOKING\n", this);
    }

    /**
     * Marks the order as cooked.
     */
    public void finishCooking() {
        status = PizzaOrderStatus.COOKED;
        System.out.printf("%s COOKED\n", this);
    }

    /**
     * Retrieves the description of the order.
     *
     * @return The order text.
     */
    public String getOrderText() {
        return orderText;
    }

    @Override
    public String toString() {
        return String.format("Order{id: %d, text: %s}", id, orderText);
    }

    /**
     * Marks the order as completed.
     */
    public void complete() {
        status = PizzaOrderStatus.COMPLETED;
        System.out.printf("%s COMPLETED\n", this);
    }

    /**
     * Handles the case where the recipient is invalid.
     */
    public void invalidRecipient() {
    }

    /**
     * Checks if the order is completed.
     *
     * @return True if the order is completed, false otherwise.
     */
    public boolean isCompleted() {
        return status == PizzaOrderStatus.COMPLETED;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PizzaOrder other) {
            return id == other.id;
        }
        return false;
    }
}