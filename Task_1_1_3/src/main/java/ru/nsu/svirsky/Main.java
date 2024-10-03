package ru.nsu.svirsky;

import java.util.Scanner;

public class Main {
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
