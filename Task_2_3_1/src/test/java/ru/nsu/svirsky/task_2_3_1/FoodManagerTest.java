package ru.nsu.svirsky.task_2_3_1;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import ru.nsu.svirsky.task_2_3_1.models.FoodManager;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;

class FoodManagerTest {
    private FoodManager foodManager;
    private List<Coordinates> availableCoords;

    @BeforeEach
    void setUp() {
        foodManager = new FoodManager(5);
        availableCoords = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            availableCoords.add(new Coordinates(i, i));
        }
    }

    @Test
    void testFillField() {
        foodManager.fillField(availableCoords);
        List<Coordinates> foodCoords = foodManager.getFoodCoords();
        assertTrue(foodCoords.size() <= 5);
        for (Coordinates coord : foodCoords) {
            assertTrue(availableCoords.contains(coord));
        }
    }

    @Test
    void testEatFood() {
        foodManager.fillField(availableCoords);
        Coordinates foodCoords = foodManager.getFoodCoords().get(0);
        assertTrue(foodManager.eat(foodCoords));
        assertFalse(foodManager.getFoodCoords().contains(foodCoords));
    }

    @Test
    void testEatNonExistentFood() {
        foodManager.fillField(availableCoords);
        Coordinates nonExistentCoords = new Coordinates(100, 100);
        assertFalse(foodManager.eat(nonExistentCoords));
    }

    @Test
    void testResetFoodList() {
        foodManager.fillField(availableCoords);
        foodManager.reset();
        assertTrue(foodManager.getFoodCoords().isEmpty());
    }
}
