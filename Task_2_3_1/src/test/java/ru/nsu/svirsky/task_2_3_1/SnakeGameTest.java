package ru.nsu.svirsky.task_2_3_1;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.task_2_3_1.models.SnakeGame;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;
import ru.nsu.svirsky.task_2_3_1.utils.GameConfig;
import ru.nsu.svirsky.task_2_3_1.utils.GameState;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class SnakeGameTest {
    private SnakeGame game;

    @BeforeEach
    void setUp() {
        GameConfig config = new GameConfig(10, 10, 0, 0, 1, 5, new Coordinates(0, 0), 1);
        game = new SnakeGame(config);
    }

    @Test
    void testGameLoopFood() {
        game.gameLoop(DirectionOfMovement.RIGHT);
        assertEquals(GameState.RUNNING, game.getState());
    }

    @Test
    void testRestart() {
        game.gameLoop(DirectionOfMovement.RIGHT);
        game.restart();
        assertEquals(GameState.RUNNING, game.getState());
        assertEquals(new Coordinates(0, 0), game.getSnakeCoords().get(0));
    }

    @Test
    void testGetFreeCells() {
        List<Coordinates> freeCells = game.getFreeCells();
        assertTrue(freeCells.size() > 0);
    }

    @Test
    void testCheckBoardCollision() {
        Coordinates outOfBounds = new Coordinates(10, 10);
        assertTrue(game.checkBoardCollision(outOfBounds));
    }

    @Test
    void testGetRobotSnakesCoords() {
        List<Coordinates> robotCoords = game.getRobotSnakesCoords();
        assertNotNull(robotCoords);
    }
}
