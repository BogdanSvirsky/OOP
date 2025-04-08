package ru.nsu.svirsky.task_2_3_1.models;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;

public record GameField(int columnsCount, int rowsCount) {
    public List<Coordinates> getCells() {
        List<Coordinates> result = new ArrayList<>();
        for (int i = 0; i < columnsCount; i++) {
            for (int j = 0; j < rowsCount; j++) {
                result.add(new Coordinates(i, j));
            }
        }
        return result;
    }
}
