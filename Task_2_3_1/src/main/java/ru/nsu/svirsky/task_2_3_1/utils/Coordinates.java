package ru.nsu.svirsky.task_2_3_1.utils;

public record Coordinates(int x, int y) {
    public Coordinates getNeighbor(DirectionOfMovement direction) {
        return switch (direction) {
            case UP -> new Coordinates(x, y - 1);
            case DOWN -> new Coordinates(x, y + 1);
            case LEFT -> new Coordinates(x - 1, y);
            case RIGHT -> new Coordinates(x + 1, y);
            case null -> new Coordinates(x, y);
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Coordinates other) {
            return this.x == other.x && this.y == other.y;
        }
        return false;
    }
}
