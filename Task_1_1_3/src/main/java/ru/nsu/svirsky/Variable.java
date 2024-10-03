package ru.nsu.svirsky;

public class Variable extends Expression {
    String variableName;

    public Variable(String variableName) {
        this.variableName = variableName;
    }

    @Override
    public double eval(String vars) {
        String[] keyValuePair;
        String[] varsValues;

        vars = vars.replaceAll(" ", "");

        varsValues = vars.split(";");

        for (String varValue : varsValues) {
            keyValuePair = varValue.split("=");
            if (variableName.equals(keyValuePair[0])) {
                return Integer.parseInt(keyValuePair[1]);
            }
        }

        System.out.printf("\n(!) Variable %s hasn't value\n", variableName);

        return 0;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(var.equals(variableName)? 1: 0);
    }

    @Override
    public String toString() {
        return variableName;
    }

    @Override
    public boolean hasVariables() {
        return true;
    }

    @Override
    public Expression simplify() {
        return new Variable(variableName);
    }
}
