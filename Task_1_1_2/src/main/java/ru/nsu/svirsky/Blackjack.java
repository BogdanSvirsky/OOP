package ru.nsu.svirsky;

import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.enums.OpenCardAction;
import ru.nsu.svirsky.enums.RoundState;

/**
 * Implementation of Blackjack game logic.
 *
 * @author Bogdan Svirsky
 */
public class Blackjack {
    /**
     * Method for hand out moment changing state logic.
     *
     * @param state Blackjack state
     */
    public static void handOutCards(BlackjackState state) {
        state.player.takeCard(state.deck.getCard());
        state.player.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        checkState(state);
    }

    /**
     * Method which change state like opening card in Blackjack.
     *
     * @param state Blackjack state
     * @param action open card action type
     * @return card which was opened 
     */
    public static Card openCard(BlackjackState state, OpenCardAction action) {
        Card card = null;

        switch (action) {
            case PLAYER_OPEN_CARD:
                card = state.deck.getCard();
                state.player.takeCard(card);
                break;
            case DEALER_OPEN_CARD:
                card = state.deck.getCard();
                state.dealer.takeCard(card);
                break;
            case DEALER_OPEN_CLOSED_CARD:
                card = state.dealer.openClosedCard();
                break;
            default:
                System.out.println("(?) Unexpected action value in Blackjack.openCard");
                break;
        }

        checkState(state);

        return card;
    }

    /**
     * Method for checking current state for Blackjack or overflow scores.
     *
     * @param state Blackjack state
     */
    public static void checkState(BlackjackState state) {
        if (state.roundState != RoundState.IN_PROGRESS) {
            return;
        }

        if (state.player.getScore() > 21) {
            dealerWins(state);
        } else if (state.dealer.getScore() > 21) {
            playerWins(state);
        } else if (!state.dealer.getIsCardClosed()
            && (state.player.hasBlackjack() || state.dealer.hasBlackjack())) { // check Blackjack
            if (state.player.hasBlackjack() && state.dealer.hasBlackjack()) {
                draw(state);
            } else if (state.player.hasBlackjack()) {
                playerWins(state);
            } else {
                dealerWins(state);
            }
        }
    }

    /**
     * Method for implementing ending round moment state changing logic.
     *
     * @param state Blackjac state, which will be changing to the end of round state
     */
    public static void endRound(BlackjackState state) {
        if (state.roundState != RoundState.IN_PROGRESS) {
            return;
        }

        if (state.player.getScore() > state.dealer.getScore()) {
            playerWins(state);
        } else if (state.dealer.getScore() > state.player.getScore()) {
            dealerWins(state);
        } else {
            draw(state);
        }
    }

    /**
     * Method for implementing starting new round
     * (take the cards from the player and the dealer, etc.).
     *
     * @param state Blackjack state, which will change to the new round state
     */
    public static void nextRound(BlackjackState state) {
        state.player.reload();
        state.dealer.reload();
        state.roundNumber++;
        state.roundState = RoundState.IN_PROGRESS;
    }

    /**
     * Method for implementing player win moment state changing logic.
     *
     * @param state Blackjac state, which will be changing to player win state
     */
    public static void playerWins(BlackjackState state) {
        state.roundState = RoundState.PLAYER_WINS;
        state.playersPoints++;
    }

    /**
     * Method for implementing dealer win moment state changing logic.
     *
     * @param state Blackjac state, which will be changing to dealer win state
     */
    public static void dealerWins(BlackjackState state) {
        state.roundState = RoundState.DEALER_WINS;
        state.dealersPoints++;
    }

    /**
     * Method for implementing draw moment state changing logic.
     *
     * @param state Blackjac state, which will be changing to draw state
     */
    public static void draw(BlackjackState state) {
        state.roundState = RoundState.DRAW;
        state.dealersPoints++;
        state.playersPoints++;
    }
}