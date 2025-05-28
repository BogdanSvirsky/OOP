package ru.nsu.svirsky.task_2_3_1.models;

import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;

public record Food(Coordinates coords) {
    public void onEaten() {
         System.out.println("Food has eaten!");
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Food other) {
            return ((Food) obj).coords.equals(other.coords);
        }
        return false;
    }
}
