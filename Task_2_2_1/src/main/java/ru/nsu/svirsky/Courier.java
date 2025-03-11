package ru.nsu.svirsky;

import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.StorageForCourier;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Courier<IdType> extends Thread {
    private final IdType courierId;
    private final int backpackSize;
    private final List<Pizza> currentPizzas = new ArrayList<>();
    private final int deliveringTime;
    private final AtomicBoolean finishWork = new AtomicBoolean(false);
    private final StorageForCourier storage;

    public Courier(IdGetter<IdType> idGetter, int backpackSize, StorageForCourier storage,
                   int deliveringTime) {
        this.backpackSize = backpackSize;
        this.storage = storage;
        this.deliveringTime = deliveringTime;
        courierId = idGetter.get();
        start();
    }

    @Override
    public void run() {
        while (!finishWork.get()) {
            fillBackpack(true);
            deliver();
        }

        fillBackpack(false);

        while (!currentPizzas.isEmpty()) {
            deliver();
            fillBackpack(false);
        }

        System.out.printf("%s finish work\n", this);
    }

    private void fillBackpack(boolean isWaiting) {
        while (currentPizzas.size() < backpackSize && !storage.isEmpty()) {
            if (isWaiting) {
                try {
                    currentPizzas.add(storage.get());
                } catch (InterruptedException e) {
                    if (!finishWork.get()) {
                        System.err.printf("%s backpack filling was interrupted!", this);
                    }
                    return;
                }
            } else {
                Pizza pizza = storage.noWaitGet();
                if (pizza == null) {
                    System.out.printf("%s can't find pizza in storage\n", this);
                    return;
                } else {
                    currentPizzas.add(pizza);
                }
            }
        }
        System.out.printf("%s backpack was filled\n", this);
    }

    private void deliver() {
        try {
            sleep(deliveringTime);
        } catch (InterruptedException e) {
            System.err.printf("%s delivering was interrupted\n", this);
            return;
        }
        while (!currentPizzas.isEmpty()) {
            Pizza pizza = currentPizzas.removeFirst();
            System.out.printf("%s delivered %s\n", this, pizza);
            pizza.order.pizzaRecipient.get(pizza);
        }
    }

    public void finishWork() {
        finishWork.set(true);
        if (getState() == State.WAITING) {
            interrupt();
        }
    }

    @Override
    public String toString() {
        return String.format("Courier{id: %s}", courierId);
    }
}
