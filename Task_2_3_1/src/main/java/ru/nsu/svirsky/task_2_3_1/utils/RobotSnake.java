package ru.nsu.svirsky.task_2_3_1.utils;

import java.util.List;
import ru.nsu.svirsky.task_2_3_1.models.Snake;

public abstract class RobotSnake extends Snake {
    public RobotSnake(Coordinates startCoords) {
        super(startCoords);
    }

    public abstract DirectionOfMovement getNextMove(List<Coordinates> foodCoords);
}
