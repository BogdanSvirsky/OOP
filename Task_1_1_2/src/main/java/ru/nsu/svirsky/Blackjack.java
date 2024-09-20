package ru.nsu.svirsky;

import java.util.*;

enum EndRoundEvent {
    PLAYERWINS, DEALERWINS, DRAW
}

enum OpenCardAction {
    PLAYER_OPEN_CARD, DEALER_OPEN_CARD, DEALER_OPEN_CLOSED_CARD
}

public class Blackjack {
    private static int roundNumber = 1;
    private static int playersPoints = 0;
    private static int dealersPoints = 0;
    private static Hand player = new Hand(), dealer = new Hand();
    private static Deck deck = new Deck();
    private static boolean isPlayerMoves = true;
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            System.out.printf("Раунд %d\n", roundNumber);

            playRound();
        }
    }

    private static void printCards() {
        LinkedList<Card> cards = player.getCards();

        System.out.print("\tВаши карты: [");
        for (int i = 0; i < cards.size(); i++) {
            System.out.print(cards.get(i));
            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");

        System.out.printf(" => %d\n", player.getScore());

        cards = dealer.getCards();
        System.out.print("\tКарты дилера: [");
        for (int i = 0; i < cards.size(); i++) {
            if (isPlayerMoves && i == cards.size() - 1) {
                System.out.print("<закрытая карта>");
            } else {
                System.out.print(cards.get(i));
            }

            if (i != cards.size() - 1) {
                System.out.print(", ");
            }
        }
        System.out.print("]");

        if (!isPlayerMoves) {
            System.out.printf(" => %d\n", dealer.getScore());
        }

        System.out.println();
    }

    private static void openCard(OpenCardAction action) {
        Card card;
        switch (action) {
            case PLAYER_OPEN_CARD:
                card = deck.getCard();
                System.out.printf("Вы открыли карту %s\n", card);
                player.takeCard(card);
                break;
            case DEALER_OPEN_CARD:
                card = deck.getCard();
                System.out.printf("Дилер открыли карту %s\n", card);
                dealer.takeCard(card);
                break;
            case DEALER_OPEN_CLOSED_CARD:
                System.out.printf("Дилер открывает закрытую карту %s\n", dealer.getCards().getLast());
                break;
        }
        printCards();
        pause(1);
    }

    private static void endRound(EndRoundEvent event) {
        switch (event) {
            case PLAYERWINS:
                System.out.print("Вы выиграли раунд! ");
                playersPoints++;
                break;
            case DEALERWINS:
                System.out.print("Вы проиграли раунд! ");
                dealersPoints++;
                break;
            case DRAW:
                System.out.print("Ничья в этом раунде! ");
                playersPoints++;
                dealersPoints++;
                break;
        }
        printPoints();
    }

    private static void printPoints() {
        System.out.printf("Счёт %d:%d", playersPoints, dealersPoints);
        if (playersPoints > dealersPoints) {
            System.out.println(" в Вашу пользу.");
        } else if (playersPoints < dealersPoints) {
            System.out.println(" в пользу Дилера.");
        } else {
            System.out.printf(", ничья.\n");
        }
    }

    public static void playRound() {
        player.reload();
        dealer.reload();
        roundNumber++;
        isPlayerMoves = true;

        String userInput;
        player.takeCard(deck.getCard());
        player.takeCard(deck.getCard());
        dealer.takeCard(deck.getCard());
        dealer.takeCard(deck.getCard());
        System.out.println("Дилер раздал карты");
        printCards();

        System.out.printf("Ваш ход\n-------\n");

        if (player.hasBlackjack()) {
            System.out.println("(!) У вас Блэкджек");
        } else {
            while (player.getScore() < 21 && isPlayerMoves) {
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться ...");
                userInput = scanner.next();
                if (userInput.equals("1")) {
                    openCard(OpenCardAction.PLAYER_OPEN_CARD);
                } else if (userInput.equals("0")) {
                    break;
                } else {
                    System.out.println("(!) Неверный ввод, попробуйте ещё раз.");
                }
            }
        }

        isPlayerMoves = false;

        if (player.getScore() > 21) {
            endRound(EndRoundEvent.DEALERWINS);
            return;
        }

        System.out.printf("Ход дилера\n-------\n");
        openCard(OpenCardAction.DEALER_OPEN_CLOSED_CARD);

        if (dealer.hasBlackjack()) {
            System.out.println("(!) У Дилера Блэкджек");
        } else {
            while (dealer.getScore() < 17) {
                openCard(OpenCardAction.DEALER_OPEN_CARD);
            }
        }

        // check Blackjack
        if (player.hasBlackjack() && dealer.hasBlackjack()) {
            endRound(EndRoundEvent.DRAW);
            return;
        } else if (player.hasBlackjack()) {
            endRound(EndRoundEvent.PLAYERWINS);
            return;
        } else if (dealer.hasBlackjack()) {
            endRound(EndRoundEvent.DEALERWINS);
            return;
        }

        // find winner
        if (dealer.getScore() > 21) {
            endRound(EndRoundEvent.PLAYERWINS);
        } else {
            if (player.getScore() > dealer.getScore()) {
                endRound(EndRoundEvent.PLAYERWINS);
            } else if (dealer.getScore() > player.getScore()) {
                endRound(EndRoundEvent.DEALERWINS);
            } else {
                endRound(EndRoundEvent.DRAW);
            }
        }
        pause(2);
    }

    private static void pause(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("(?) Thread InteruptedException");
        }
    }
}