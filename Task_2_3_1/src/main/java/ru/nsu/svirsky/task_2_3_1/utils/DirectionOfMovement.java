package ru.nsu.svirsky.task_2_3_1.utils;

import java.util.Random;

public enum DirectionOfMovement {
    UP, DOWN, LEFT, RIGHT;

    public boolean isOpposite(DirectionOfMovement direction) {
        return this == LEFT && direction == RIGHT || this == RIGHT && direction == LEFT
                || this == UP && direction == DOWN || this == DOWN && direction == UP;
    }

    public static DirectionOfMovement getRandom() {
        Random random = new Random();
        return DirectionOfMovement.values()[random.nextInt(DirectionOfMovement.values().length)];
    }
}
