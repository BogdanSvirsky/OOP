package ru.nsu.svirsky;

import java.util.Scanner;

/**
 * Implementation of user I/O with expressions.
 *
 * @author Svirsky Bogdan
 */
public class Main {
    /**
     * Main method.
     *
     * @param args string arguments (isn't used)
     */
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter expression (don't forget brackets!): ");
        Expression expr = Parser.parse(scanner.nextLine());
        System.out.print("Entet assignment (separate by ';'): ");
        try {
            System.out.println("Result: " + String.valueOf(expr.eval(scanner.nextLine())));
        } catch (ArithmeticException e) {
            System.out.println("(!) " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}
