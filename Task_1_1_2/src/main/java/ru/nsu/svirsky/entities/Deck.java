package ru.nsu.svirsky.entities;

import java.util.Collections;
import java.util.LinkedList;

import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;
import ru.nsu.svirsky.utils.Notifier;

public class Deck {
    private LinkedList<Card> cards = new LinkedList<Card>();
    private  final Notifier notifier;

    public Deck(Notifier notifier) {
        this.notifier = notifier;
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
                notifier.notify("(!) Карты в колоде кончились. Колода обновлена.");
            }

            return card;
        }
    }
}
