package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.entities.Deck;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    @Test
    void reloadTest() {
        Deck deck = new Deck((t) -> {
        });

        for (int i = 0; i < 60; i++) {
            assertTrue(deck.getCard() != null);
        }
    }

    @Test
    void getCardTest() {
        Deck deck = new Deck((t) -> {
        });

        assertNotNull(deck.getCard());
    }
}
