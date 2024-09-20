package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


public class HandTest {
    @Test
    void takeCardTest() {
        Card card1 = new Card(Rank.ACE, Suit.SPADES);
        Hand hand = new Hand();

        hand.takeCard(card1);

        assertEquals(hand.getCards().getLast(), card1);
    }

    @Test
    void reloadTest() {
        Hand hand = new Hand();

        hand.takeCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.takeCard(new Card(Rank.EIGHT, Suit.SPADES));
        hand.takeCard(new Card(Rank.TWO, Suit.DIAMONDS));

        hand.reload();

        assertTrue(hand.getCards().isEmpty());
    }

    @Test
    void getScoreTest() {
        Hand hand = new Hand();

        hand.takeCard(new Card(Rank.ACE, Suit.HEARTS));
        hand.takeCard(new Card(Rank.ACE, Suit.SPADES));
        hand.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));

        assertEquals(hand.getScore(), 3);
    }

    @Test
    void hasBlackjackTest() {
        Hand hand = new Hand();

        hand.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        hand.takeCard(new Card(Rank.ACE, Suit.SPADES));

        assertTrue(hand.hasBlackjack());
    }
}
