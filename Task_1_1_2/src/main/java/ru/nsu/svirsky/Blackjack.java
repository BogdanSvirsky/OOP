package ru.nsu.svirsky;

import java.util.*;

public class Blackjack {
    private static int roundNumber = 1;
    private static int playersPoints = 0;
    private static int dealersPoints = 0;
    private static Hand player = new Hand(), dealer = new Hand();
    private static Deck deck = new Deck();
    private static boolean isPlayerMoves = true;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Добро пожаловать в Блэкджек!");

        while (true) {
            if (roundNumber > 1)
                break;

            System.out.printf("Раунд %d\n", roundNumber);

            player.takeCard(deck.getCard());
            player.takeCard(deck.getCard());
            dealer.takeCard(deck.getCard());
            dealer.takeCard(deck.getCard());

            System.out.println("Дилер раздал карты");
            printCards();

            if (player.getScore() == 21) {
                playerWins();
                continue;
            }

            System.out.printf("Ваш ход\n-------\n");

            while (player.getScore() < 21 && isPlayerMoves) {
                System.out.println("Введите “1”, чтобы взять карту, и “0”, чтобы остановиться ...");
                switch (scanner.nextInt()) {
                    case 1:
                        playerOpenCard();
                        printCards();
                        break;
                    case 0:
                        isPlayerMoves = false;
                        break;
                }
            }

            if (player.getScore() > 21) {
                playerLoses();
                continue;
            }

            System.out.printf("Ход дилера\n-------\n");
            System.out.printf("Дилер открывает закрытую карту %s\n", dealer.getCards().getLast());
            printCards();

            if (dealer.getScore() == 21) {
                playerLoses();
                continue;
            }

            while (dealer.getScore() < 17) {
                dealerOpenCard();
                printCards();
            }

            if (dealer.getScore() > 21) {
                playerWins();
            } else {
                if (player.getScore() > dealer.getScore()) {
                    playerWins();
                } else if (dealer.getScore() > player.getScore()) {
                    playerLoses();
                } else {
                    draw();
                }
            }

            player.reload();
            dealer.reload();
            roundNumber++;
            isPlayerMoves = true;
        }
        scanner.close();
    }

    private static void printCards() {
        ArrayList<Card> cards = player.getCards();

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

    private static void playerOpenCard() {
        Card card = deck.getCard();
        System.out.printf("Вы открыли карту %s\n", card);
        player.takeCard(card);
    }

    private static void dealerOpenCard() {
        Card card = deck.getCard();
        System.out.printf("Дилер открыли карту %s\n", card);
        dealer.takeCard(card);
    }

    private static void playerWins() {
        System.out.print("Вы выиграли раунд! ");
        playersPoints++;
        printPoints();
    }

    private static void playerLoses() {
        System.out.print("Вы проиграли раунд! ");
        dealersPoints++;
        printPoints();
    }

    private static void draw() {
        System.out.print("Ничья в этом раунде! ");
        playersPoints++;
        dealersPoints++;
        printPoints();
    }

    private static void printPoints() {
        System.out.print("Счёт ");
        if (playersPoints > dealersPoints) {
            System.out.printf("%d:%d в Вашу пользу.\n", playersPoints, dealersPoints);
        } else if (playersPoints < dealersPoints) {
            System.out.printf("%d:%d в пользу Дилера.\n", dealersPoints, playersPoints);
        } else {
            System.out.printf("%d:%d в ничью пользу.\n", playersPoints, dealersPoints);
        }
    }
}