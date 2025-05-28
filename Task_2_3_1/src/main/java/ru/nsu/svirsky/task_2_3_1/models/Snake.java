package ru.nsu.svirsky.task_2_3_1.models;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;
import ru.nsu.svirsky.task_2_3_1.utils.DirectionOfMovement;

public class Snake {
    protected List<Coordinates> coordinatesList = new LinkedList<>();
    public final Coordinates startCoords;

    public Snake(Coordinates startCoords) {
        this.startCoords = startCoords;
        reset();
    }

    public void move(DirectionOfMovement direction, boolean isFoodEaten) {
        Coordinates head = coordinatesList.getLast();
        Coordinates newHead = head.getNeighbor(direction);
        coordinatesList.add(newHead);
        if (!isFoodEaten) {
            coordinatesList.removeFirst();
        }
    }

    public void reset() {
        coordinatesList.clear();
        coordinatesList.add(startCoords);
    }

    public List<Coordinates> getCoordinatesList() {
        return new ArrayList<>(coordinatesList);
    }

    public Coordinates getHead() {
        return coordinatesList.getLast();
    }
}
