package ru.nsu.svirsky.interfaces;

import ru.nsu.svirsky.pizzeria.Pizza;

/**
 * Interface for receiving a pizza.
 *
 * @author BogdanSvirsky
 */
public interface PizzaRecipient {
    /**
     * Handles the received pizza.
     *
     * @param pizza The pizza to be received.
     */
    void get(Pizza pizza);
}