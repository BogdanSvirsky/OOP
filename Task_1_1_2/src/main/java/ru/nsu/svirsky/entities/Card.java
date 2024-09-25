package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;

public class Card {
    private Rank rank;
    private Suit suit;

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    @Override
    public String toString() {
        return String.format("%s %s (%d)", rank.name, suit.name, rank.value);
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }
}