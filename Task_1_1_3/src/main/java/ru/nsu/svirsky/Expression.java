package ru.nsu.svirsky;

public abstract class Expression {
    public void print() {
        System.out.println(this.toString());
    };

    public abstract String toString();

    public abstract Expression derivative(String var);

    public abstract float eval(String vars);

    public abstract boolean hasVariables();

    public abstract Expression simplify();
}
