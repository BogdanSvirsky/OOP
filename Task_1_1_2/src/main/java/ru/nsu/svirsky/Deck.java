package ru.nsu.svirsky;

import java.util.*;

public class Deck {
    private ArrayList<Card> cards = new ArrayList<>();

    public Deck() {
        initCards();
    }

    private void initCards() {
        cards.clear();
        for (Rank rank : Rank.values()) {
            for (Suit suit : Suit.values()) {
                cards.add(new Card(rank, suit));
            }
        }
        Collections.shuffle(cards);
    }

    void reload() {
        initCards();
    }

    public Card getCard() {
        if (cards.isEmpty()) {
            return null;
        } else {
            Card card = cards.removeLast();

            if (cards.isEmpty()) {
                reload();
                System.out.println("(!) Карты в колоде кончились. Колода обновлена.");
            }

            return card;
        }
    }
}
