package ru.nsu.svirsky;

public class Number extends Expression {
    private float value;

    public Number(float value) {
        this.value = value;
    }

    @Override
    public float eval(String vars) {
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
