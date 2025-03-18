package ru.nsu.svirsky.config;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import ru.nsu.svirsky.pizzeria.Baker;
import ru.nsu.svirsky.pizzeria.Courier;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Loads pizzeria configuration from a JSON file.
 * @author BogdanSvirsky
 */
public class SimplePizzeriaConfig implements PizzeriaConfig {
    private final JsonNode configHead;
    private final AtomicInteger bakersCount = new AtomicInteger(0);
    private final AtomicInteger couriersCount = new AtomicInteger(0);

    /**
     * Creates a new configuration from a JSON file.
     *
     * @param pathToConfig Path to the JSON file.
     * @throws IOException If the file cannot be read or parsed.
     */
    public SimplePizzeriaConfig(String pathToConfig) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        configHead = objectMapper.readTree(new File(pathToConfig));
    }

    @Override
    public List<Baker> getBakers() {
        List<Baker> bakers = new ArrayList<>();
        for (JsonNode node : configHead.get("bakers")) {
            bakers.add(new Baker(
                    () -> bakersCount.addAndGet(1), node.get("workingTimeMillis").asLong()));
        }
        return bakers;
    }

    @Override
    public List<Courier> getCouriers() {
        List<Courier> couriers = new ArrayList<>();
        for (JsonNode node : configHead.get("couriers")) {
            couriers.add(new Courier(
                    () -> couriersCount.addAndGet(1), node.get("backpackSize").asInt(),
                    node.get("deliveringTime").asInt()));
        }
        return couriers;
    }

    @Override
    public int getPizzaStorageCapacity() {
        return configHead.get("pizzaStorageCapacity").asInt(0);
    }
}