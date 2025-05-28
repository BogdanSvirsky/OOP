package ru.nsu.svirsky.task_2_3_1.utils;

public record GameConfig(int columnsCount, int rowsCount, int width, int height, int foodCount,
                         int winLen, Coordinates snakeStartCoords, int robotSnakesCount) {
    public int getCellSide() {
        return Math.min(width / columnsCount, height / rowsCount);
    }
}
