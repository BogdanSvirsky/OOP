package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.entities.Dealer;
import ru.nsu.svirsky.entities.Deck;
import ru.nsu.svirsky.entities.Player;
import ru.nsu.svirsky.enums.OpenCardAction;
import ru.nsu.svirsky.enums.Rank;
import ru.nsu.svirsky.enums.RoundState;
import ru.nsu.svirsky.enums.Suit;

/**
 * Test class for Blackjack game logic.
 *
 * @author Bogdan Svirsky
 */
public class BlackjackTest {
    @Test
    void nextRoundTest() {
        Player player = new Player();
        Dealer dealer = new Dealer();

        dealer.takeCard(new Card(Rank.EIGHT, Suit.HEARTS));
        player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        player.takeCard(new Card(Rank.JACK, Suit.CLUBS));
        player.takeCard(new Card(Rank.KING, Suit.CLUBS));

        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), player, dealer);

        Blackjack.nextRound(state);

        assertEquals(state.roundNumber, 2);
        assertEquals(state.roundState, RoundState.IN_PROGRESS);
        assertEquals(dealer.getCards().length, 0);
        assertEquals(player.getCards().length, 0);
    }

    @Test
    void endRoundTest() {
        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), new Player(), new Dealer());

        state.player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        state.player.takeCard(new Card(Rank.FIVE, Suit.CLUBS));
        state.player.takeCard(new Card(Rank.SIX, Suit.CLUBS));
        state.dealer.takeCard(new Card(Rank.EIGHT, Suit.HEARTS));
        state.dealer.takeCard(new Card(Rank.QUEEN, Suit.HEARTS));

        Blackjack.checkState(state);

        Blackjack.endRound(state);

        assertEquals(state.roundState, RoundState.PLAYER_WINS);
        assertEquals(state.playersPoints, 1);
        Blackjack.nextRound(state);

        state.player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        state.player.takeCard(new Card(Rank.FIVE, Suit.CLUBS));
        state.dealer.takeCard(new Card(Rank.FIVE, Suit.CLUBS));
        state.dealer.takeCard(new Card(Rank.SIX, Suit.HEARTS));
        state.dealer.takeCard(new Card(Rank.QUEEN, Suit.HEARTS));

        Blackjack.checkState(state);

        Blackjack.endRound(state);

        assertEquals(state.roundState, RoundState.DEALER_WINS);
        assertEquals(state.dealersPoints, 1);
        Blackjack.nextRound(state);

        state.player.takeCard(new Card(Rank.QUEEN, Suit.CLUBS));
        state.player.takeCard(new Card(Rank.TWO, Suit.CLUBS));
        state.player.takeCard(new Card(Rank.NINE, Suit.CLUBS));
        state.dealer.takeCard(new Card(Rank.FIVE, Suit.CLUBS));
        state.dealer.takeCard(new Card(Rank.SIX, Suit.HEARTS));
        state.dealer.takeCard(new Card(Rank.QUEEN, Suit.HEARTS));

        Blackjack.checkState(state);

        Blackjack.endRound(state);

        assertEquals(state.roundState, RoundState.DRAW);
        assertEquals(state.dealersPoints, 2);
        assertEquals(state.playersPoints, 2);
        Blackjack.nextRound(state);

    }

    @Test
    void hangOutCardsTest() {
        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), new Player(), new Dealer());

        Blackjack.handOutCards(state);

        assertEquals(state.player.getCards().length, 2);
        assertEquals(state.dealer.getCards().length, 2);
    }

    @Test
    void openCardTest() {
        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), new Player(), new Dealer());

        Blackjack.handOutCards(state);

        Blackjack.openCard(state, OpenCardAction.PLAYER_OPEN_CARD);
        assertEquals(state.player.getCards().length, 3);
        Blackjack.openCard(state, OpenCardAction.DEALER_OPEN_CLOSED_CARD);
        assertTrue(!state.dealer.cardsToString().contains("<закрытая карта>"));
        Blackjack.openCard(state, OpenCardAction.DEALER_OPEN_CARD);
        assertEquals(state.dealer.getCards().length, 3);
    }

    @Test
    void checkStateTest() {
        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), new Player(), new Dealer());

        state.player.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.TWO, Suit.DIAMONDS));
        state.dealer.openClosedCard();
        Blackjack.checkState(state);

        assertEquals(state.roundState, RoundState.PLAYER_WINS);
        Blackjack.nextRound(state);

        state.dealer.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.TWO, Suit.DIAMONDS));
        state.dealer.openClosedCard();
        Blackjack.checkState(state);

        assertEquals(state.roundState, RoundState.DEALER_WINS);
        Blackjack.nextRound(state);

        state.dealer.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.ACE, Suit.HEARTS));
        state.player.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        state.dealer.openClosedCard();
        Blackjack.checkState(state);

        assertEquals(state.roundState, RoundState.DRAW);
        Blackjack.nextRound(state);

        state.dealer.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        state.dealer.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.TWO, Suit.DIAMONDS));
        state.dealer.openClosedCard();
        Blackjack.checkState(state);

        assertEquals(state.roundState, RoundState.PLAYER_WINS);
        Blackjack.nextRound(state);

        state.dealer.takeCard(new Card(Rank.ACE, Suit.DIAMONDS));
        state.dealer.takeCard(new Card(Rank.TEN, Suit.HEARTS));
        state.player.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.TEN, Suit.DIAMONDS));
        state.player.takeCard(new Card(Rank.TWO, Suit.DIAMONDS));
        state.dealer.openClosedCard();
        Blackjack.checkState(state);

        assertEquals(state.roundState, RoundState.DEALER_WINS);
        Blackjack.nextRound(state);

        assertEquals(state.playersPoints, 3);
        assertEquals(state.dealersPoints, 3);
    }

    @Test
    void checkWinTest() {
        BlackjackState state = new BlackjackState(
                new Deck((a) -> {
                }), new Player(), new Dealer());

        Blackjack.playerWins(state);
        assertEquals(state.playersPoints, 1);
        assertEquals(state.roundState, RoundState.PLAYER_WINS);

        Blackjack.dealerWins(state);
        assertEquals(state.dealersPoints, 1);
        assertEquals(state.roundState, RoundState.DEALER_WINS);

        Blackjack.draw(state);
        assertEquals(state.dealersPoints, 2);
        assertEquals(state.playersPoints, 2);
        assertEquals(state.roundState, RoundState.DRAW);
    }
}
