package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.entities.Dealer;
import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.Suit;

/**
 * Test class for dealer game entity.
 * 
 * @author Bogdan Svirsky
 */
public class DealerTest {
    @Test
    void isMovesTest() {
        Dealer dealer = new Dealer();

        dealer.takeCard(new Card(Rank.TEN, Suit.CLUBS));
        dealer.takeCard(new Card(Rank.SIX, Suit.CLUBS));

        assertTrue(dealer.getIsMoves());

        dealer.takeCard(new Card(Rank.ACE, Suit.SPADES));

        assertFalse(dealer.getIsMoves());
    }

    @Test
    void cardsToStringTest() {
        Dealer dealer = new Dealer();
        Card card1 = new Card(Rank.ACE, Suit.DIAMONDS);
        Card card2 = new Card(Rank.EIGHT, Suit.SPADES);

        dealer.takeCard(card1);
        dealer.takeCard(card2);

        assertEquals(
                dealer.cardsToString(),
                "[" + card1 + ", <закрытая карта>]");

        dealer.openClosedCard();

        assertEquals(
                dealer.cardsToString(),
                String.format(
                        "[%s, %s] => %d",
                        card1, card2, card1.getRank().value + card2.getRank().value));
    }

}
