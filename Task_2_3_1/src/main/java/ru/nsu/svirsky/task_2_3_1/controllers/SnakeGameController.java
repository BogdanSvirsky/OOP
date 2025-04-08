package ru.nsu.svirsky.task_2_3_1.controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.util.Duration;
import ru.nsu.svirsky.task_2_3_1.models.GameField;
import ru.nsu.svirsky.task_2_3_1.models.SnakeGame;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;
import ru.nsu.svirsky.task_2_3_1.utils.GameConfig;
import ru.nsu.svirsky.task_2_3_1.views.SnakeGameView;

public class SnakeGameController {
    private DirectionOfMovement currDirect = null;
    private KeyCode lastReleasedKey = null;
    @FXML
    private Canvas canvas;
    private SnakeGameView snakeGameView;
    private Timeline timeline;
    private boolean isWaitingRestart = false;
    private SnakeGame game;

    @FXML
    public void initialize() {
        GameConfig config = new GameConfig(20, 20, (int) canvas.getWidth(),
                (int) canvas.getHeight(), 5, 10, new Coordinates(0, 0), 3);
        game = new SnakeGame(config);
        snakeGameView = new SnakeGameView(canvas, config.getCellSide());
        canvas.setFocusTraversable(true);
        timeline = new Timeline(new KeyFrame(Duration.millis(200), e -> update()));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private void update() {
        processCurrDirection();
        game.gameLoop(currDirect);
        switch (game.getState()) {
            case WIN -> win();
            case RUNNING -> snakeGameView.update(game.getSnakeCoords(), game.getRobotSnakesCoords(),
                    game.getFoodCoords());
            case GAME_OVER -> gameOver();
        }
    }


    private void processCurrDirection() {
        if (lastReleasedKey == null) {
            return;
        }
        DirectionOfMovement newDir = switch (lastReleasedKey) {
            case LEFT -> DirectionOfMovement.LEFT;
            case RIGHT -> DirectionOfMovement.RIGHT;
            case UP -> DirectionOfMovement.UP;
            case DOWN -> DirectionOfMovement.DOWN;
            default -> currDirect;
        };
        if (currDirect == null || !currDirect.isOpposite(newDir)) {
            currDirect = newDir;
        }
    }

    private void gameOver() {
        timeline.stop();
        isWaitingRestart = true;
        snakeGameView.printGameOver();
    }

    private void restart() {
        isWaitingRestart = false;
        currDirect = null;
        lastReleasedKey = null;
        game.restart();
        timeline.play();
    }

    private void win() {
        timeline.stop();
        snakeGameView.printWin();
        isWaitingRestart = true;
    }

    @FXML
    public void onKeyReleased(KeyEvent e) {
        lastReleasedKey = e.getCode();
        if (isWaitingRestart && lastReleasedKey == KeyCode.R) {
            restart();
        }
    }
}
