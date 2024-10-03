package ru.nsu.svirsky;

public class Number extends Expression {
    private double value;

    public Number(double value) {
        this.value = value;
    }

    @Override
    public double eval(String vars) {
        return value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public String toString() {
        return String.valueOf(value);
    }

    @Override
    public boolean hasVariables() {
        return false;
    }

    @Override
    public Expression simplify() {
        return new Number(value);
    }
}
