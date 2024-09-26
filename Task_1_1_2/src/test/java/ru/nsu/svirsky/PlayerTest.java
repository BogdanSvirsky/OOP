package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.entities.Player;
import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;

/**
 * Test class for player game entity.
 * 
 * @author Bogdan Svirsky
 */
public class PlayerTest {
    @Test
    void checkIsMoves() {
        Player player = new Player();

        player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        player.takeCard(new Card(Rank.QUEEN, Suit.DIAMONDS));
        player.takeCard(new Card(Rank.QUEEN, Suit.HEARTS));

        assertFalse(player.getIsMoves());
    }

    @Test
    void checkReload() {
        Player player = new Player();

        player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        player.takeCard(new Card(Rank.QUEEN, Suit.DIAMONDS));
        player.takeCard(new Card(Rank.QUEEN, Suit.HEARTS));

        assertFalse(player.getIsMoves());

        player.reload();

        assertTrue(player.getIsMoves());
        assertEquals(player.getCards().length, 0);
    }

    @Test
    void cardsToStringTest() {
        Card[] cards = new Card[] { new Card(Rank.EIGHT, Suit.CLUBS),
            new Card(Rank.EIGHT, Suit.HEARTS) };
        Player player = new Player();

        player.takeCard(cards[0]);
        player.takeCard(cards[1]);

        assertArrayEquals(player.getCards(), cards);

        assertEquals(
                player.cardsToString(),
                "[" + cards[0] + ", " + cards[1] 
                + "] => " + String.valueOf(player.getScore()));
    }
}
