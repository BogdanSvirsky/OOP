package ru.nsu.svirsky.enums;

/**
 * Enum for comfortable work with card's suit.
 * 
 * @author Bogdan Svirsky
 */
public enum Suit {
    CLUBS("Крести"), DIAMONDS("Буби"), HEARTS("Черви"), SPADES("Пики");

    public String name;

    Suit(String name) {
        this.name = name;
    }
}
