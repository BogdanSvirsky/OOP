package ru.nsu.svirsky.config;

import java.util.List;
import ru.nsu.svirsky.pizzeria.Baker;
import ru.nsu.svirsky.pizzeria.Courier;

/**
 * Configuration interface for a pizzeria.
 *
 * @author BogdanSvirsky
 */
public interface PizzeriaConfig {
    /**
     * Get pizzeria bakers.
     *
     * @return List of bakers.
     */
    List<Baker> getBakers();

    /**
     * Get pizzeria couriers.
     *
     * @return List of couriers.
     */
    List<Courier> getCouriers();

    /**
     * Get pizza storage capacity.
     *
     * @return Pizza storage capacity.
     */
    int getPizzaStorageCapacity();
}