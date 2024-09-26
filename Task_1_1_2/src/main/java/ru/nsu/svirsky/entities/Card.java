package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;
/**
 * Class represents card as a game entity.
 * 
 * @author Bogdan Svirsky
 */
public class Card {
    private Rank rank;
    private Suit suit;

    /**
     * Constructs card, which particular rank and suit.
     * 
     * @param rank which rank card will has
     * @param suit which suit card will has
     */
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }

    /**
     * Overriding of toString() method to simplify work with user output.
     * 
     * @return a string in the "{rank} {suit} {value}" format
     */
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