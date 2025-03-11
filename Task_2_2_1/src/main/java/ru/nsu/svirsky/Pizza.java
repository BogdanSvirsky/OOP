package ru.nsu.svirsky;

public class Pizza {
    public final PizzaOrder order;
    private final String name;

    public Pizza(PizzaOrder order, String name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Pizza{name: %s, order: %s}", name, order);
    }
}
