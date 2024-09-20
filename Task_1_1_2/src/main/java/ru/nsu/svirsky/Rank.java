package ru.nsu.svirsky;

public enum Rank {
    TWO(2, "Двойка"), THREE(3, "Тройка"), FOUR(4, "Четвёрка"),
    FIVE(5, "Пятёрка"), SIX(6, "Шестёрка"), SEVEN(7, "Семёрка"),
    EIGHT(8, "Восьмёрка"), NINE(9, "Девятка"), TEN(10, "Десятка"),
    JACK(10, "Валет"), QUEEN(10, "Королева"), KING(10, "Король"),
    ACE(11, "Туз");

    int value;
    String name;

    Rank(int value, String name) {
        this.name = name;
        this.value = value;
    }
}