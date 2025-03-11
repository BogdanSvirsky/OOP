package ru.nsu.svirsky;

import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.PizzaRecipient;

enum PizzaOrderStatus {
    CREATED, COOKING, COOKED, COMPLETED
}

public class PizzaOrder<IdType> {
    private final IdType id;
    private final String orderText;

    private PizzaOrderStatus status = PizzaOrderStatus.CREATED;
    public final PizzaRecipient pizzaRecipient;

    public PizzaOrder(IdGetter<IdType> idGetter, String orderText, PizzaRecipient pizzaRecipient) {
        this.id = idGetter.get();
        this.orderText = orderText;
        this.pizzaRecipient = pizzaRecipient;
        System.out.printf("%s CREATED\n", this);
    }

    public void startCooking() {
        status = PizzaOrderStatus.COOKING;
        System.out.printf("%s COOKING\n", this);
    }

    public void finishCooking() {
        status = PizzaOrderStatus.COOKED;
        System.out.printf("%s COOKED\n", this);
    }

    public String getOrderText() {
        return orderText;
    }

    @Override
    public String toString() {
        return String.format("Order{id: %d, text: %s}", id, orderText);
    }

    public void complete() {
        status = PizzaOrderStatus.COMPLETED;
        System.out.printf("%s COMPLETED\n", this);
    }

    public void invalidRecipient() {
    }

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
