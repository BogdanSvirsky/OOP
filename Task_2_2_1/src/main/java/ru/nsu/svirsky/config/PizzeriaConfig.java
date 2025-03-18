package ru.nsu.svirsky.config;

import ru.nsu.svirsky.pizzeria.Baker;
import ru.nsu.svirsky.pizzeria.Courier;

import java.util.List;

/**
 * Configuration interface for a pizzeria.
 * @author BogdanSvirsky
 */
public interface PizzeriaConfig {
    /**
     * @return List of bakers.
     */
    List<Baker> getBakers();

    /**
     * @return List of couriers.
     */
    List<Courier> getCouriers();

    /**
     * @return Pizza storage capacity.
     */
    int getPizzaStorageCapacity();
}