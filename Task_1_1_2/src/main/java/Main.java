
import java.util.Scanner;

import ru.nsu.svirsky.Blackjack;
import ru.nsu.svirsky.entities.BlackjackState;
import ru.nsu.svirsky.entities.Dealer;
import ru.nsu.svirsky.entities.Deck;
import ru.nsu.svirsky.entities.Player;
import ru.nsu.svirsky.enums.OpenCardAction;
import ru.nsu.svirsky.enums.RoundState;

public class Main {
    private static Deck deck = new Deck((text) -> System.out.println(text));
    private static Player player = new Player();
    private static Dealer dealer = new Dealer();
    private static BlackjackState state = new BlackjackState(deck, player, dealer);
    private static String userInput;
    private static Scanner scanner = new Scanner(System.in);

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

    public static void playRound() {
        System.out.printf("Раунд %d\n", state.roundNumber);

        Blackjack.handOutCards(state);
        System.out.println("Дилер раздал карты");
        printCards();

        System.out.printf("Ваш ход\n-------\n");
        if (player.hasBlackjack()) {
            System.out.println("(!) У вас Блэкджек");
        } else {
            while (state.player.getIsMoves()) {
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

            if (dealer.hasBlackjack()) {
                System.out.println("(!) У Дилера Блэкджек");
            } else {
                while (dealer.getIsMoves()) {
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
        System.out.print("\tВаши карты: " + player.cardsToString() + "\n");

        System.out.print("\tКарты дилера: " + dealer.cardsToString() + "\n");

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
