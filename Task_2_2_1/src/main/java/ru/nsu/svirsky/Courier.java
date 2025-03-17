package ru.nsu.svirsky;

import ru.nsu.svirsky.exceptions.InvalidExecutorExecption;
import ru.nsu.svirsky.interfaces.IdGetter;
import ru.nsu.svirsky.interfaces.QueueForConsumer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class Courier<IdType> {
    private final IdType courierId;
    private final int backpackSize;
    private final List<Pizza> currentPizzas = new ArrayList<>();
    private final int deliveringTime;
    private QueueForConsumer<Pizza> storage;
    private final AtomicBoolean finishWork = new AtomicBoolean(false);

    public void setStorage(QueueForConsumer<Pizza> storage) {
        this.storage = storage;
    }

    private final Thread executor = new Thread(() -> {
        try {
            runLifecycle();
        } catch (InvalidExecutorExecption e) {
            throw new RuntimeException("Courier's executor can run its code!");
        }
    });

    public Courier(IdGetter<IdType> idGetter, int backpackSize, int deliveringTime) {
        this.backpackSize = backpackSize;
        this.deliveringTime = deliveringTime;
        courierId = idGetter.get();
    }

    private void runLifecycle() throws InvalidExecutorExecption {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExecption();
        }
        while (!finishWork.get()) {
            fillBackpack(true);
            deliver();
        }

        fillBackpack(true);

        while (!currentPizzas.isEmpty()) {
            deliver();
            fillBackpack(false);
        }

        System.out.printf("%s finish work\n", this);
    }

    private void fillBackpack(boolean isWaiting) throws InvalidExecutorExecption {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExecption();
        }
        while (currentPizzas.size() < backpackSize && !storage.isEmpty()) {
            if (isWaiting) {
                try {
                    currentPizzas.add(storage.get());
                } catch (InterruptedException e) {
                    if (!finishWork.get()) {
                        System.err.printf("%s backpack filling was interrupted!", this);
                        return;
                    } else {
                        break;
                    }
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
        System.out.printf("%s has filled backpack\n", this);
    }

    private void deliver() throws InvalidExecutorExecption {
        if (Thread.currentThread() != executor) {
            throw new InvalidExecutorExecption();
        }
        try {
            Thread.sleep(deliveringTime);
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

    public void beginWork() {
        finishWork.set(false);
        executor.start();
    }

    public void finishWork() {
        finishWork.set(true);
        if (executor.getState() == Thread.State.WAITING) {
            executor.interrupt();
        }
    }

    @Override
    public String toString() {
        return String.format("Courier{id: %s}", courierId);
    }
}
