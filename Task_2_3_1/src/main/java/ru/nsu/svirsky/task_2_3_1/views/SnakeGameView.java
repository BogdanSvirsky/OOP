package ru.nsu.svirsky.task_2_3_1.views;

import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;

public class SnakeGameView {
    private final Canvas canvas;
    private final GraphicsContext gc;
    private final int cellSide;

    public SnakeGameView(Canvas canvas, int cellSide) {
        this.canvas = canvas;
        this.gc = canvas.getGraphicsContext2D();
        this.cellSide = cellSide;
    }

    public void update(List<Coordinates> snakeCoords,
                       List<Coordinates> robotSnakesCoords, List<Coordinates> foodList) {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.GREEN);
        gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.YELLOW);
        for (Coordinates coords : snakeCoords) {
            gc.fillRect(cellSide * coords.x(), cellSide * coords.y(), cellSide, cellSide);
        }
        gc.setFill(Color.RED);
        for (Coordinates coords : robotSnakesCoords) {
            gc.fillRect(cellSide * coords.x(), cellSide * coords.y(), cellSide, cellSide);
        }
        gc.setFill(Color.AQUA);
        for (Coordinates coords : foodList) {
            gc.fillRect(cellSide * coords.x(), cellSide * coords.y(), cellSide, cellSide);
        }
    }

    public void printGameOver() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillText("GAME OVER!!!", 10, 10);
        gc.fillText("PRESS R TO RESTART", 10, 50);
    }

    public void printWin() {
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        gc.setFill(Color.BLACK);
        gc.fillText("YOU ARE WINNER!!!", 10, 10);
        gc.fillText("PRESS R TO RESTART", 10, 50);
    }
}
