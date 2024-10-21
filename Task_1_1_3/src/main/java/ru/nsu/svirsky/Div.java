package ru.nsu.svirsky;

/**
 * Implementation of division expression.
 *
 * @author Svirsky Bogdan
 */
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
                        new Mul(divisible.derivative(var), divider.clone()),
                        new Mul(divisible.clone(), divider.derivative(var))),
                new Mul(divider.clone(), divider.clone()));
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

        if (simplifiedDivider.equals(simplifiedDivider)) {
            return new Number(1);
        }

        if (simplifiedDivisible instanceof Number
                && simplifiedDivisible.eval("") == 0) {
            return new Number(0);
        } else {
            res = new Div(simplifiedDivisible, simplifiedDivider);
        }

        if (!res.hasVariables()) {
            res = new Number(res.eval(""));
        }

        return res;
    }

    public Expression getDivisible() {
        return divisible.clone();
    }

    public Expression getDivider() {
        return divider.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Div other) {
            return this.divisible.equals(other.getDivisible())
                    && this.divider.equals(other.getDivider());
        }

        return false;
    }

    @Override
    public Expression clone() {
        return new Div(divisible.clone(), divider.clone());
    }
}
