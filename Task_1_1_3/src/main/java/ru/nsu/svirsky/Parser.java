package ru.nsu.svirsky;

/**
 * Implementation of expression's parser.
 *
 * @author Svirsky Bogdan
 */
public class Parser {
    private static final String 
            LANGUAGE = "([a-zA-Z]|([0-9]+((\\.)[0-9]+)?)|(\\(|\\))|\\+|\\-|/|\\*|\\=)";

    private static String expression = null;
    private static int currentIndex = 0;

    /**
     * Main method of parser.
     *
     * @param expr string expression
     * @return new Expression object
     */
    public static Expression parse(String expr) {
        expression = expr.replaceAll("[^" + LANGUAGE + "]", "");
        currentIndex = 0;

        return parseExpression();
    }

    private static String readToken() {
        if (currentIndex == expression.length()) {
            return "";
        }

        char symb = expression.charAt(currentIndex++);

        if (symb == '+' || symb == '-' || symb == '*'
                || symb == '/' || symb == '(' || symb == ')') {
            return String.valueOf(symb);
        }

        int left = currentIndex - 1;

        while (currentIndex < expression.length() 
            && expression.substring(left, currentIndex + 1)
                .matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
            currentIndex++;
        }

        if (currentIndex + 1 < expression.length() 
            && expression.substring(left, currentIndex + 2)
                .matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
            currentIndex += 2;
        }

        while (currentIndex < expression.length() 
            && expression.substring(left, currentIndex + 1)
                .matches("([0-9]+((\\.)[0-9]+)?)|([a-zA-Z]+)")) {
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
        Expression firstExp = parseMonome();
        Expression res = firstExp;

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
        Expression firstExpr = parseAtom();
        Expression res = firstExpr;

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
                isNegative = false;
            } else {
                res = new Variable(readToken());
            }
        }

        if (isNegative) {
            res = new Mul(new Number(-1), res);
        }

        return res;
    }
}
