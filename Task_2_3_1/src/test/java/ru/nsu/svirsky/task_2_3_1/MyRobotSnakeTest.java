package ru.nsu.svirsky.task_2_3_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ru.nsu.svirsky.task_2_3_1.models.MyRobotSnake;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;

class MyRobotSnakeTest {
    private MyRobotSnake myRobotSnake;
    private List<Coordinates> foodCoords;

    @BeforeEach
    void setUp() {
        myRobotSnake = new MyRobotSnake(new Coordinates(0, 0));
        foodCoords = List.of(
                new Coordinates(2, 2),
                new Coordinates(3, 1),
                new Coordinates(4, 4)
        );
    }

    @Test
    void testGetNextMoveTowardsFood() {
        DirectionOfMovement nextMove = myRobotSnake.getNextMove(foodCoords);
        assertTrue(nextMove == DirectionOfMovement.RIGHT || nextMove == DirectionOfMovement.DOWN);
    }

    @Test
    void testGetNextMoveWithEmptyFoodList() {
        DirectionOfMovement nextMove = myRobotSnake.getNextMove(List.of());
        assertNotNull(nextMove);
    }

    @Test
    void testGetNextMoveAvoidingOppositeDirection() {
        myRobotSnake.move(DirectionOfMovement.UP, false);
        DirectionOfMovement nextMove = myRobotSnake.getNextMove(foodCoords);
        assertNotEquals(DirectionOfMovement.DOWN, nextMove);
    }

    @Test
    void testFindNearestFood() {
        Coordinates head = new Coordinates(1, 1);
        Coordinates nearestFood = myRobotSnake.findNearestFood(head, foodCoords);
        assertEquals(new Coordinates(2, 2), nearestFood);
    }
}
