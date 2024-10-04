package ru.nsu.svirsky;

/**
 * Main abstract class which declares basic methods of expression.
 *
 * @author Svirsky Bogdan
 */
public abstract class Expression {
    public void print() {
        System.out.println(this.toString());
    }

    public abstract String toString();

    public abstract Expression derivative(String var);

    public abstract double eval(String vars);

    public abstract boolean hasVariables();

    public abstract Expression simplify();

    public abstract Expression clone();
}
