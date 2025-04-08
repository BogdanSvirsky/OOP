package ru.nsu.svirsky.task_2_3_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import ru.nsu.svirsky.task_2_3_1.models.Snake;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;

class SnakeTest {
    private Snake snake;

    @BeforeEach
    void setUp() {
        snake = new Snake(new Coordinates(0, 0));
    }

    @Test
    void testMove() {
        snake.move(DirectionOfMovement.RIGHT, false);
        assertEquals(new Coordinates(1, 0), snake.getHead());
    }

    @Test
    void testMoveWithFood() {
        snake.move(DirectionOfMovement.RIGHT, true);
        assertEquals(new Coordinates(1, 0), snake.getHead());
    }

    @Test
    void testReset() {
        snake.move(DirectionOfMovement.RIGHT, false);
        snake.reset();
        assertEquals(new Coordinates(0, 0), snake.getHead());
    }

    @Test
    void testGetCoordinatesList() {
        snake.move(DirectionOfMovement.RIGHT, true);
        List<Coordinates> coords = snake.getCoordinatesList();
        assertEquals(2, coords.size());
        assertEquals(new Coordinates(0, 0), coords.get(0));
        assertEquals(new Coordinates(1, 0), coords.get(1));
    }
}
