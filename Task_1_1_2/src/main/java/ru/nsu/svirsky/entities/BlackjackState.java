package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.enums.RoundState;

public class BlackjackState {
    public int roundNumber = 1;
    public int playersPoints = 0;
    public int dealersPoints = 0;
    public final Player player;
    public final Dealer dealer;
    public final Deck deck;
    public RoundState roundState;

    public BlackjackState(Deck deck, Player player, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        roundState = RoundState.IN_PROGRESS;
    }

    public void reset() {
        player.reload();
        dealer.reload();
        roundState = RoundState.IN_PROGRESS;
    }

    public void check() {
        if (player.getScore() > 21) {
            roundState = RoundState.DEALER_WINS;
        } else if (dealer.getScore() > 21) {
            roundState = RoundState.PLAYER_WINS;
        } else if (player.hasBlackjack() || dealer.hasBlackjack()) { // check Blackjack
            if (player.hasBlackjack() && dealer.hasBlackjack()) {
                roundState = RoundState.DRAW;
            } else if (player.hasBlackjack()) {
                roundState = RoundState.PLAYER_WINS;
            } else if (dealer.hasBlackjack()) {
                roundState = RoundState.DEALER_WINS;
            }
        } else if (dealer.getScore() > 21) {
            roundState = RoundState.PLAYER_WINS;
        }
    }

    public void finishRound() {
        if (roundState != RoundState.IN_PROGRESS) {
            return;
        }

        if (player.getScore() > dealer.getScore()) {
            roundState = RoundState.PLAYER_WINS;
        } else if (dealer.getScore() > player.getScore()) {
            roundState = RoundState.DEALER_WINS;
        } else {
            roundState = RoundState.DRAW;
        }
    }
}
