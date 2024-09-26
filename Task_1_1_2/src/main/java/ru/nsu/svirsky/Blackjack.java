package ru.nsu.svirsky;

import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.enums.OpenCardAction;
import ru.nsu.svirsky.enums.RoundState;

public class Blackjack {
    public static void handOutCards(BlackjackState state) {
        state.player.takeCard(state.deck.getCard());
        state.player.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        checkState(state);
    }

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
        }

        checkState(state);

        return card;
    }

    public static void checkState(BlackjackState state) {
        if (state.roundState != RoundState.IN_PROGRESS) {
            return;
        }

        if (state.player.getScore() > 21) {
            dealerWins(state);
        } else if (state.dealer.getScore() > 21) {
            playerWins(state);
        } else if (state.player.hasBlackjack() || state.dealer.hasBlackjack()) { // check Blackjack
            if (state.player.hasBlackjack() && state.dealer.hasBlackjack()) {
                draw(state);
            } else if (state.player.hasBlackjack()) {
                playerWins(state);
            } else {
                dealerWins(state);
            }
        }
    }

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

    public static void nextRound(BlackjackState state) {
        state.player.reload();
        state.dealer.reload();
        state.roundNumber++;
        state.roundState = RoundState.IN_PROGRESS;
    }

    public static void playerWins(BlackjackState state) {
        state.roundState = RoundState.PLAYER_WINS;
        state.playersPoints++;
    }

    public static void dealerWins(BlackjackState state) {
        state.roundState = RoundState.DEALER_WINS;
        state.dealersPoints++;
    }

    public static void draw(BlackjackState state) {
        state.roundState = RoundState.DRAW;
        state.dealersPoints++;
        state.playersPoints++;
    }
}