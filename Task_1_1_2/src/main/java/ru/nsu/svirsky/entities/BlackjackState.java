package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.enums.RoundState;

/**
 * Class for controlling Blackjack game state.
 *
 * @author Bogdan Svirsky
 */
public class BlackjackState {
    public int roundNumber = 1;
    public int playersPoints = 0;
    public int dealersPoints = 0;
    public final Player player;
    public final Dealer dealer;
    public final Deck deck;
    public RoundState roundState;

    /**
     * Class constructor.
     *
     * @param deck cards storage
     * @param player user game enity 
     * @param dealer bot game entity
     */
    public BlackjackState(Deck deck, Player player, Dealer dealer) {
        this.player = player;
        this.dealer = dealer;
        this.deck = deck;
        roundState = RoundState.IN_PROGRESS;
    }
}
