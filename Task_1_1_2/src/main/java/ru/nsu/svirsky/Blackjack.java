package ru.nsu.svirsky;

import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Card;
import ru.nsu.svirsky.enums.OpenCardAction;

public class Blackjack {
    public static void handOutCards(BlackjackState state) {
        state.player.takeCard(state.deck.getCard());
        state.player.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        state.dealer.takeCard(state.deck.getCard());
        state.check();
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

        state.check();

        return card;
    }

    public static void endRound(BlackjackState state) {
        state.finishRound();

        switch (state.roundState) {
            case PLAYER_WINS:
                state.playersPoints++;
                break;
            case DEALER_WINS:
                state.dealersPoints++;
                break;
            case DRAW:
                state.playersPoints++;
                state.dealersPoints++;
                break;
            case IN_PROGRESS:
                return;
        }

        state.roundNumber++;
    }
}