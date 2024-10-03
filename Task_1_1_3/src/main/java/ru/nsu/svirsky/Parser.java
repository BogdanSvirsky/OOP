package ru.nsu.svirsky;

public class Parser {
    private static final String LANGUAGE = "([a-zA-Z]|([0-9]+((\\.)[0-9]+)?)|(\\(|\\))|\\+|\\-|/|\\*|\\=)";
    private static String expression = null;
    private static int currentIndex = 0;

    public static Expression parse(String expr) {
        expression = expr.replaceAll("[^" + LANGUAGE + "]", "");
        currentIndex = 0;

        return parseExpression();
    }

    private static String readToken() {
        if (currentIndex == expression.length())
            return "";

        char symb = expression.charAt(currentIndex++);

        if (symb == '+' || symb == '-' || symb == '*'
                || symb == '/' || symb == '(' || symb == ')') {
            return String.valueOf(symb);
        }

        int left = currentIndex - 1;

        while (currentIndex < expression.length() &&
                expression.substring(left, currentIndex + 1).matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
            currentIndex++;
        }

        if (currentIndex + 1 < expression.length() &&
                expression.substring(left, currentIndex + 2).matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
            currentIndex += 2;
        }
        
        while (currentIndex < expression.length() &&
                expression.substring(left, currentIndex + 1).matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
            currentIndex++;
        }

        return expression.substring(left, currentIndex);
    }

    private static String peekToken() {
        int oldIndex = currentIndex;
        String result = readToken();
        currentIndex = oldIndex;
        return result;
    }

    private static Expression parseExpression() {
        Expression firstExp = parseMonome(), res = firstExp;

        if (peekToken().equals("+") || peekToken().equals("-")) {
            if (readToken().equals("+")) {
                res = new Add(firstExp, parseMonome());
            } else {
                res = new Sub(firstExp, parseMonome());
            }
        }

        return res;
    }

    private static Expression parseMonome() {
        Expression firstExpr = parseAtom(), res = firstExpr;

        if (peekToken().equals("*") || peekToken().equals("/")) {
            if (readToken().equals("*")) {
                res = new Mul(firstExpr, parseAtom());
            } else {
                res = new Div(firstExpr, parseAtom());
            }
        }

        return res;
    }

    private static Expression parseAtom() {
        Expression res;
        boolean isNegative = false;

        if (peekToken().equals("-")) {
            isNegative = true;
            readToken();
        }

        if (peekToken().equals("(")) {
            readToken();
            res = parseExpression();
            readToken();
        } else {
            if (peekToken().matches("([0-9]+((\\.)[0-9]+)?)")) {
                res = new Number((isNegative ? -1 : 1) * Double.parseDouble(readToken()));
            } else {
                res = new Variable(readToken());
            }
        }

        return res;
    }
}
