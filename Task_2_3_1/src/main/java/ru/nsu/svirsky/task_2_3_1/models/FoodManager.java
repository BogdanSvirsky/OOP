package ru.nsu.svirsky.task_2_3_1.models;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import ru.nsu.svirsky.task_2_3_1.utils.Coordinates;

public class FoodManager {
    private final int foodCount;
    private final List<Food> foodList = new ArrayList<>();

    public FoodManager(int foodCount) {
        this.foodCount = foodCount;
    }

    public void fillField(List<Coordinates> availableCoords) {
        List<Coordinates> coords = new ArrayList<>(availableCoords);
        int countFoodToGenerate = Math.min(availableCoords.size(), foodCount - foodList.size());
        Random random = new Random();
        System.out.println(countFoodToGenerate);
        for (int i = 0; i < countFoodToGenerate; i++) {
            foodList.add(new Food(coords.remove(random.nextInt(coords.size()))));
        }
    }

    public boolean eat(Coordinates coords) {
        return foodList.removeIf(food -> {
            boolean match = food.coords().equals(coords);
            if (match) {
                food.onEaten();
            }
            return match;
        });
    }

    public void reset() {
        foodList.clear();
    }

    public List<Coordinates> getFoodCoords() {
        List<Coordinates> result = new ArrayList<>();
        for (Food food : foodList) {
            result.add(food.coords());
        }
        return result;
    }
}
