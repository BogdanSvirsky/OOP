package ru.nsu.svirsky;

/**
 * Implementation of adding expression.
 *
 * @author Svirsky Bogdan
 */
public class Add extends Expression {
    private Expression firstSummand;
    private Expression secondSummand;

    public Add(Expression firstSummand, Expression secondSummand) {
        this.firstSummand = firstSummand;
        this.secondSummand = secondSummand;
    }

    @Override
    public double eval(String vars) {
        return firstSummand.eval(vars) + secondSummand.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(firstSummand.derivative(var), secondSummand.derivative(var));
    }

    @Override
    public String toString() {
        return String.format("(%s + %s)", firstSummand, secondSummand);
    }

    @Override
    public boolean hasVariables() {
        return firstSummand.hasVariables() || secondSummand.hasVariables();
    }

    @Override
    public Expression simplify() {
        Expression res;
        Expression simplifiedFirstSummand = firstSummand.simplify();
        Expression simplifiedSecondSummand = secondSummand.simplify();

        if (simplifiedFirstSummand instanceof Number 
            && simplifiedFirstSummand.eval("") == 0) {
            res = simplifiedSecondSummand;
        } else if (simplifiedSecondSummand instanceof Number 
            && simplifiedSecondSummand.eval("") == 0) {
            res = simplifiedFirstSummand;
        } else {
            res = new Add(simplifiedFirstSummand, simplifiedSecondSummand);
        }

        if (!res.hasVariables()) {
            res = new Number(res.eval(""));
        }

        return res;
    }

    @Override
    public Expression clone() {
        return new Add(firstSummand.clone(), secondSummand.clone());
    }
}
