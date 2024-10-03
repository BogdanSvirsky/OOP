package ru.nsu.svirsky;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));
        Expression ee = Parser.parse("2.25");

        e.print();
        ee.print();

        Expression de = e.derivative("x");
        de.print();
        de.simplify().print();
        Parser.parse("((0 * x) + (2 * x))").simplify().print();

        e = new Add(new Number(3), new Mul(new Number(2),
                new Variable("x")));

        double result = e.eval("x = 10; y = 13");
        System.out.println(result);
        System.out.println(ee.eval("x=10"));
    }
}
