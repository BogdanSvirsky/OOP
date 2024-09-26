package ru.nsu.svirsky.entities;

/**
 * Representation of player game entity.
 * 
 * @author Svirsky Bogdan
 */
public class Player extends Hand {
    private boolean isMoves = true;

    @Override
    public void takeCard(Card card) {
        super.takeCard(card);
        isMoves = getScore() < 21;
    }

    public boolean getIsMoves() {
        return isMoves;
    }
    
    /**
     * Method for generating human-readable representation of player's cards.
     * 
     * @return string with cards and score
     */
    public String cardsToString() {
        String result = "[";
        Card[] cards = getCards();

        for (int i = 0; i < cards.length; i++) {
            result += cards[i];
            if (i != cards.length - 1) {
                result += ", ";
            }
        }
        result += "]";

        result += String.format(" => %d", getScore());

        return result;
    }

    @Override
    public void reload() {
        isMoves = true;
        super.reload();
    }
}
