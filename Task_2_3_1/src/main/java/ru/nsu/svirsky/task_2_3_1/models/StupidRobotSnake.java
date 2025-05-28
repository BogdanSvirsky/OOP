package ru.nsu.svirsky.task_2_3_1.models;

import java.util.List;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;
import ru.nsu.svirsky.task_2_3_1.utils.RobotSnake;

public class StupidRobotSnake extends RobotSnake {
    private DirectionOfMovement currentDirection = null;

    public StupidRobotSnake(Coordinates startCoords) {
        super(startCoords);
    }

    @Override
    public void move(DirectionOfMovement direction, boolean isFoodEaten) {
        currentDirection = direction;
        super.move(direction, isFoodEaten);
    }

    @Override
    public DirectionOfMovement getNextMove(List<Coordinates> foodCoords) {
        DirectionOfMovement result = DirectionOfMovement.getRandom();

        if (currentDirection != null && currentDirection.isOpposite(result)) {
            result = switch (result) {
                case UP -> DirectionOfMovement.RIGHT;
                case LEFT -> DirectionOfMovement.UP;
                case RIGHT -> DirectionOfMovement.DOWN;
                case DOWN -> DirectionOfMovement.LEFT;
                case null -> null;
            };
        }

        return result;
    }
}
