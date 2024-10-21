package ru.nsu.svirsky;

/**
 * Implementation of subtraction expression.
 *
 * @author Svirsky Bogdan
 */
public class Sub extends Expression {
    private Expression diminutive;
    private Expression deductible;

    public Sub(Expression diminutive, Expression deductible) {
        this.diminutive = diminutive;
        this.deductible = deductible;
    }

    @Override
    public double eval(String vars) {
        return diminutive.eval(vars) - deductible.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(diminutive.derivative(var), deductible.derivative(var));
    }

    @Override
    public String toString() {
        return String.format("(%s - %s)", diminutive, deductible);
    }

    @Override
    public boolean hasVariables() {
        return diminutive.hasVariables() || deductible.hasVariables();
    }

    @Override
    public Expression simplify() {
        Expression res;
        Expression simplifiedDiminutive = diminutive.simplify();
        Expression simplifiedDeductible = deductible.simplify();

        if (simplifiedDeductible.equals(simplifiedDiminutive)) {
            return new Number(0);
        }

        if (simplifiedDiminutive instanceof Number
                && simplifiedDiminutive.eval("") == 0) {
            res = new Mul(new Number(-1), simplifiedDeductible);
        } else if (simplifiedDeductible instanceof Number
                && simplifiedDeductible.eval("") == 0) {
            res = simplifiedDiminutive;
        } else {
            res = new Sub(simplifiedDiminutive, simplifiedDeductible);
        }

        if (!res.hasVariables()) {
            res = new Number(res.eval(""));
        }

        return res;
    }

    @Override
    public Expression clone() {
        return new Sub(diminutive.clone(), deductible.clone());
    }

    public Expression getDiminutive() {
        return diminutive.clone();
    }

    public Expression getDeductible() {
        return deductible.clone();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Sub other) {
            return this.deductible.equals(other.getDeductible())
                    && this.diminutive.equals(other.getDiminutive());
        }

        return false;
    }
}
