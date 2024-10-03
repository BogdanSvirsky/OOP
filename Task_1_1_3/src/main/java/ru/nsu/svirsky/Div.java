package ru.nsu.svirsky;

public class Div extends Expression {
    private Expression divisible;
    private Expression divider;

    public Div(Expression divisible, Expression divider) {
        this.divisible = divisible;
        this.divider = divider;
    }

    @Override
    public double eval(String vars) throws ArithmeticException {
        if (divider.eval(vars) == 0) {
            throw new ArithmeticException("Division by zero in " + this);
        }
        return divisible.eval(vars) / divider.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
                new Sub(
                        new Mul(divisible.derivative(var), divider),
                        new Mul(divisible, divider.derivative(var))),
                new Mul(divider, divider));
    }

    @Override
    public String toString() {
        return String.format("(%s / %s)", divisible, divider);
    }

    @Override
    public boolean hasVariables() {
        return divider.hasVariables() || divisible.hasVariables();
    }

    @Override
    public Expression simplify() {
        Expression res;
        Expression simplifiedDivisible = divisible.simplify();
        Expression simplifiedDivider = divider.simplify();

        if (simplifiedDivisible instanceof Number &&
                simplifiedDivisible.eval("") == 0) {
            return new Number(0);
        } else {
            res = new Div(simplifiedDivisible, simplifiedDivider);
        }

        if (!res.hasVariables()) {
            res = new Number(res.eval(""));
        }

        return res;
    }
}
