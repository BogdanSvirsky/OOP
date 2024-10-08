package ru.nsu.svirsky;

import java.util.Scanner;
import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Dealer;
import ru.nsu.svirsky.entities.Deck;
import ru.nsu.svirsky.entities.Player;
import ru.nsu.svirsky.enums.OpenCardAction;
import ru.nsu.svirsky.enums.RoundState;

/**
 * Main class, that implements user I/O with Blackjack game.
 *
 * @author Bogdan Svirsky
 */
public class Main {
    private static BlackjackState state = new BlackjackState(
            new Deck((text) -> System.out.println(text)),
            new Player(), new Dealer());
    private static String userInput;
    private static Scanner scanner = new Scanner(System.in);

    /**
     * Main method. Catches Exceptions while game is working to prevent memory leak in scanner.
     *
     * @param args command line args, aren't used
     */
    public static void main(String[] args) {
        System.out.println("Добро пожаловать в Блэкджек!");

        try {
            while (true) {
                playRound();
            }
        } catch (Exception e) {
            scanner.close();
            System.out.println("here");
        }
    }

    /**
     * General method with one game round logic.
     */
    public static void playRound() {
        System.out.printf("Раунд %d\n", state.roundNumber);

        Blackjack.handOutCards(state);
        System.out.println("Дилер раздал карты");
        printCards();

        System.out.printf("Ваш ход\n-------\n");
        if (state.player.hasBlackjack()) {
            System.out.println("(!) У вас Блэкджек");
        } else {
            while (state.player.getIsMoves()) { // players moves
                System.out.println(
                        "Введите “1”, чтобы взять карту, и “0”, чтобы остановиться ...");
                userInput = scanner.next();
                if (userInput.equals("1")) {
                    System.out.printf(
                            "Вы открыли карту %s\n",
                            Blackjack.openCard(state, OpenCardAction.PLAYER_OPEN_CARD));
                    printCards();
                } else if (userInput.equals("0")) {
                    break;
                } else {
                    System.out.println("(!) Неверный ввод, попробуйте ещё раз.");
                }
            }
        }

        if (state.roundState == RoundState.IN_PROGRESS) {
            System.out.printf("Ход дилера\n-------\n");
            System.out.printf(
                    "Дилер открыл закрытую карту %s\n",
                    Blackjack.openCard(state, OpenCardAction.DEALER_OPEN_CLOSED_CARD));
            printCards();
            pause(1);

            if (state.dealer.hasBlackjack()) {
                System.out.println("(!) У Дилера Блэкджек");
            } else {
                while (state.dealer.getIsMoves()) { // dealer moves
                    System.out.printf(
                            "Дилер открыл карту %s\n",
                            Blackjack.openCard(state, OpenCardAction.DEALER_OPEN_CARD));
                    printCards();
                    pause(1);
                }
            }
        }

        Blackjack.endRound(state);
        printRoundResults();
        Blackjack.nextRound(state);
        pause(2);
    }

    private static void printCards() {
        System.out.print("\tВаши карты: " + state.player.cardsToString() + "\n");

        System.out.print("\tКарты дилера: " + state.dealer.cardsToString() + "\n");

        System.out.println();
    }

    private static void printRoundResults() {
        switch (state.roundState) {
            case PLAYER_WINS:
                System.out.print("Вы выиграли раунд! ");
                break;
            case DEALER_WINS:
                System.out.println("Вы проиграли раунд! ");
                break;
            case DRAW:
                System.out.println("Ничья! ");
                break;
            case IN_PROGRESS:
                System.out.println("(?) IN_PROGRESS has't round result");
                break;
            default:
                System.out.println("(?) Unexpected state.roundState value in Main");
                break;
        }

        System.out.printf("Счёт %d:%d", state.playersPoints, state.dealersPoints);
        if (state.playersPoints > state.dealersPoints) {
            System.out.println(" в Вашу пользу.");
        } else if (state.playersPoints < state.dealersPoints) {
            System.out.println(" в пользу Дилера.");
        } else {
            System.out.printf(", ничья.\n");
        }
    }

    private static void pause(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            System.out.println("(?) Thread InteruptedException");
        }
    }
}
