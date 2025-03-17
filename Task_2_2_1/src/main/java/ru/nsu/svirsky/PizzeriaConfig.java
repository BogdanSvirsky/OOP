package ru.nsu.svirsky;

import java.util.List;

public interface PizzeriaConfig {
    List<Baker> getBakers();
    List<Courier> getCouriers();

    int getPizzaStorageCapacity();
}
