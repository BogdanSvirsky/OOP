package ru.nsu.svirsky.pizzeria;

/**
 * Represents a pizza with an associated order.
 *
 * @author BogdanSvirsky
 */
public class Pizza {
    /**
     * The order associated with this pizza.
     */
    public final PizzaOrder order;

    /**
     * The name of the pizza.
     */
    private final String name;

    /**
     * Constructs a new pizza.
     *
     * @param order The order associated with the pizza.
     * @param name  The name of the pizza.
     */
    public Pizza(PizzaOrder order, String name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public String toString() {
        return String.format("Pizza{name: %s, order: %s}", name, order);
    }
}