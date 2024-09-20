package ru.nsu.svirsky;

import java.util.*;

public class Hand {
    private LinkedList<Card> cards = new LinkedList<Card>();
    private int score = 0;

    private void recountScore() {
        int result = 0;
        int acesCount = 0;
        Rank rank;

        for (Card card : cards) {
            rank = card.getRank();
            result += rank.value;
            if (rank == Rank.ACE) {
                acesCount++;
            }
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

    public LinkedList<Card> getCards() {
        return cards;
    }

    public void reload() {
        cards.clear();
    }

    public boolean hasBlackjack() {
        return cards.size() == 2 && getScore() == 21;
    }
}
