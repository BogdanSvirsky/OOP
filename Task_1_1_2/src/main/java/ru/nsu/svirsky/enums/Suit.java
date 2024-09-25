package ru.nsu.svirsky.enums;

public enum Suit {
    CLUBS("Крести"), DIAMONDS("Буби"), HEARTS("Черви"), SPADES("Пики");

    public String name;

    Suit(String name) {
        this.name = name;
    }
}
