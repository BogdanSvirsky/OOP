package ru.nsu.svirsky;

public class Pizzeria {
    private final Baker[] bakers;
    private final Courier[] couriers;

    public Pizzeria(Baker[] bakers, Courier[] couriers) {
        this.bakers = bakers;
        this.couriers = couriers;
    }

    public void finishWork() {
        for (Baker baker : bakers) {
            baker.finishWork();
        }
        for (Courier courier : couriers) {
            courier.finishWork();
        }
    }
}
