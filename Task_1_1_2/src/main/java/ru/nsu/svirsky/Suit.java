package ru.nsu.svirsky;

public enum Suit {
    CLUBS("Крести"), DIAMONDS("Буби"), HEARTS("Черви"), SPADES("Пики");

    String name;

    Suit(String name) {
        this.name = name;
    }
}
