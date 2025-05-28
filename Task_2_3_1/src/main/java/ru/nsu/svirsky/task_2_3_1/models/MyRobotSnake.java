package ru.nsu.svirsky.task_2_3_1.models;

import java.util.Comparator;
import java.util.List;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;
import ru.nsu.svirsky.task_2_3_1.utils.RobotSnake;

public class MyRobotSnake extends RobotSnake {
    private DirectionOfMovement currentDirection = null;

    public MyRobotSnake(Coordinates spawnCoords) {
        super(spawnCoords);
    }

    @Override
    public void move(DirectionOfMovement direction, boolean isFoodEaten) {
        currentDirection = direction;
        super.move(direction, isFoodEaten);
    }

    public DirectionOfMovement getNextMove(List<Coordinates> foodCoords) {
        if (foodCoords.isEmpty()) {
            return DirectionOfMovement.getRandom();
        }

        Coordinates head = getHead();
        Coordinates nearestFood = findNearestFood(head, foodCoords);

        DirectionOfMovement result;

        if (nearestFood.x() > head.x()) {
            result = DirectionOfMovement.RIGHT;
        } else if (nearestFood.x() < head.x()) {
            result = DirectionOfMovement.LEFT;
        } else if (nearestFood.y() > head.y()) {
            result = DirectionOfMovement.DOWN;
        } else if (nearestFood.y() < head.y()) {
            result = DirectionOfMovement.UP;
        } else {
            result = DirectionOfMovement.getRandom();
        }

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

    public Coordinates findNearestFood(Coordinates head, List<Coordinates> foodCoords) {
        return foodCoords.stream()
                .min(Comparator.comparingInt(f -> Math.abs(f.x() - head.x()) + Math.abs(f.y() - head.y())))
                .orElseThrow();
    }
}
