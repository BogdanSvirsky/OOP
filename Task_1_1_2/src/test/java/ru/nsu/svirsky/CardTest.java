package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;

/**
 * Test class for card game entity.
 *
 * @author Bogdan Svirsky
 */
public class CardTest {
    static Card card = new Card(Rank.ACE, Suit.HEARTS);

    @Test
    void toStringTest() {
        assertTrue(card.toString().equals("Туз Черви (11)"));
    }

    @Test
    void getRankTest() {
        assertEquals(card.getRank(), Rank.ACE);
    }

    @Test
    void getSuitTest() {
        assertEquals(card.getSuit(), Suit.HEARTS);
    }
}
