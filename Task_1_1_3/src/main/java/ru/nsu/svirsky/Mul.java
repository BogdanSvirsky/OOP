package ru.nsu.svirsky;

/**
 * Implementation of multiplication in expression.
 *
 * @author Svirsky Bogdan
 */
public class Mul extends Expression {
    private Expression firstMultiplier;
    private Expression secondMultiplier;

    public Mul(Expression firstMultiplier, Expression secondMultiplier) {
        this.firstMultiplier = firstMultiplier;
        this.secondMultiplier = secondMultiplier;
    }

    @Override
    public double eval(String vars) {
        return firstMultiplier.eval(vars) * secondMultiplier.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(firstMultiplier.derivative(var), secondMultiplier.clone()),
                new Mul(firstMultiplier.clone(), secondMultiplier.derivative(var)));
    }

    @Override
    public String toString() {
        return String.format("(%s * %s)", firstMultiplier, secondMultiplier);
    }

    @Override
    public boolean hasVariables() {
        return firstMultiplier.hasVariables() || secondMultiplier.hasVariables();
    }

    @Override
    public Expression simplify() {
        Expression res;
        Expression simplifiedFirstMultiplier = firstMultiplier.simplify();
        Expression simplifiedSecondMultiplier = secondMultiplier.simplify();

        if (simplifiedFirstMultiplier instanceof Number
                && simplifiedFirstMultiplier.eval("") == 0) {
            return new Number(0);
        } else if (simplifiedSecondMultiplier instanceof Number
                && simplifiedSecondMultiplier.eval("") == 0) {
            return new Number(0);
        } else if (simplifiedFirstMultiplier instanceof Number
                && simplifiedFirstMultiplier.eval("") == 1) {
            res = simplifiedSecondMultiplier;
        } else if (simplifiedSecondMultiplier instanceof Number
                && simplifiedSecondMultiplier.eval("") == 1) {
            res = simplifiedFirstMultiplier;
        } else {
            res = new Mul(simplifiedFirstMultiplier, simplifiedSecondMultiplier);
        }

        if (!res.hasVariables()) {
            res = new Number(res.eval(""));
        }

        return res;
    }

    @Override
    public Expression clone() {
        return new Mul(firstMultiplier.clone(), secondMultiplier.clone());
    }

    public Expression getFirstMultiplier() {
        return firstMultiplier.clone();
    }

    public Expression getSecondMultiplier() {
        return secondMultiplier.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Mul other) {
            return (this.firstMultiplier.equals(other.getFirstMultiplier())
                    && this.secondMultiplier.equals(other.getSecondMultiplier()))
                    || (this.firstMultiplier.equals(other.getSecondMultiplier())
                            && this.secondMultiplier.equals(other.getFirstMultiplier()));
        }

        return false;
    }
}
