package ru.nsu.svirsky;

public enum Suit {
    CLUBS("Трефы"), DIAMONDS("Бубны"), HEARTS("Черви"), SPADES("Пики");

    String name;

    Suit(String name) {
        this.name = name;
    }
}
