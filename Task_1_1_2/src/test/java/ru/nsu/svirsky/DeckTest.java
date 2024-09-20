package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class DeckTest {
    @Test
    void reloadTest() {
        Deck deck = new Deck();

        for (int i = 0; i < 60; i++) {
            assertTrue(deck.getCard() != null);
        }
    }
}
