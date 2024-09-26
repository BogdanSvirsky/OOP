package ru.nsu.svirsky.entities;

import java.util.LinkedList;
import ru.nsu.svirsky.enums.Rank;

/**
 * Reprentation of Hand game entity.
 * Class is abstract, because it implements the basic methods of "Hand" (something that holds cards)
 * 
 * @author Bogdan Svirsky
 */
public abstract class Hand {
    private LinkedList<Card> cards = new LinkedList<Card>();
    private int score = 0;

    private void recountScore() {
        int result = 0;
        int acesCount = 0;
        Rank rank;

        for (Card card : cards) {
            rank = card.getRank();
            if (rank == Rank.ACE) {
                acesCount++;
                card.getRank().value = 11;
            }
            result += rank.value;
        }

        if (result > 21) {
            result -= 10 * acesCount;
            for (Card card : cards) {
                if (card.getRank() == Rank.ACE) {
                    card.getRank().value = 1;
                }
            }
        }

        score = result;
    }

    public int getScore() {
        return score;
    }

    public void takeCard(Card card) {
        cards.add(card);
        recountScore();
    };

    /**
     * Method for getting all of cards from hand.
     * Creates a new static array for result to keep cards field private and make result universal.
     * 
     * @return staic array with cards
     */
    public Card[] getCards() {
        Card[] result = new Card[cards.size()];

        for (int i = 0; i < result.length; i++) {
            result[i] = cards.get(i);
        }

        return result;
    }

    public void reload() {
        cards.clear();
        recountScore();
    }

    public boolean hasBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }

    abstract String cardsToString();

    public Card getLastCard() {
        return cards.getLast();
    }
}
