package ru.nsu.svirsky.entities;

public class Dealer extends Hand {
    private boolean isMoves = true;
    private boolean isCardClosed = true;

    @Override
    public void takeCard(Card card) {
        super.takeCard(card);
        isMoves = getScore() < 17;
    }

    public boolean getIsMoves() {
        return isMoves;
    }

    public String cardsToString() {
        String result = "[";
        Card[] cards = getCards();

        for (int i = 0; i < cards.length; i++) {
            if (isCardClosed && i == cards.length - 1) {
                result += "<закрытая карта>";
            } else {
                result += cards[i];
            }

            if (i != cards.length - 1) {
                result += ", ";
            }
        }
        result += "]";

        if (!isCardClosed) {
            result += String.format(" => %d", getScore());
        }
        
        return result;
    }

    public Card openClosedCard() {
        isCardClosed = false;
        return getLastCard();
    }

    @Override
    public void reload() {
        isMoves = true;
        isCardClosed = true;
        super.reload();
    }
}
