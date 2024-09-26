package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.Deck;

/**
 * Test class for deck game entity.
 *
 * @author Bogdan Svirsky
 */
public class DeckTest {
    private static String output = "";
    
    @Test
    void reloadTest() {
        Deck deck = new Deck((t) -> {
            output = t;
        });

        for (int i = 0; i < 60; i++) {
            assertTrue(deck.getCard() != null);
        }

        assertFalse(output.isEmpty());
    }

    @Test
    void getCardTest() {
        Deck deck = new Deck((t) -> {
        });

        assertNotNull(deck.getCard());
    }
}
