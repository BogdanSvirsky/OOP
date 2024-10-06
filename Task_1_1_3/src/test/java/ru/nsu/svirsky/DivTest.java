package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for division.
 *
 * @author Svirsky Bogdan
 */
public class DivTest {
    @Test
    void basicTest() {
        assertTrue(new Div(new Variable("x"), new Number(1)).hasVariables());
        assertTrue(new Div(new Number(1), new Variable("x")).hasVariables());

        assertEquals(new Div(new Variable("x"), new Number(1)).toString(), "(x / 1.0)");

        assertEquals(
                new Div(new Variable("x"), new Number(1)).eval("x=1"), 1);
        assertEquals(
                new Div(new Number(6), new Variable("x")).eval("x=2"), 3);
    }

    @Test
    void derivativeTest() {
        assertEquals(
                new Div(new Number(1), new Variable("x")).derivative("x").eval("x=1"), -1);

        Expression divisible = new Add(new Variable("x"), new Number(5));
        Expression divider = new Add(new Mul(new Variable("x"), new Variable("x")), new Number(2));

        assertEquals(
                new Div(divisible, divider).derivative("x").eval("x=10"),
                new Div(
                        new Sub(
                                new Mul(divisible.derivative("x"), divider),
                                new Mul(divisible, divider.derivative("x"))),
                        new Mul(divider, divider)).eval("x=10"));
    }

    @Test
    void simplifyTest() {
        Expression constantsDiv = new Div(new Number(4), new Number(2));

        assertTrue(constantsDiv.simplify() instanceof Number);
        assertEquals(constantsDiv.simplify().eval(""), 2);

        Expression div = new Div(new Variable("x"), constantsDiv);

        assertTrue(div.simplify() instanceof Div);
        assertEquals(div.simplify().eval("x=10"), 5);

        div = new Div(constantsDiv, new Variable("x"));

        assertTrue(div.simplify() instanceof Div);
        assertEquals(div.simplify().eval("x=2"), 1);
    }

    @Test
    void exceptionTest() {
        try {
            new Div(new Number(1), new Number(0)).eval("");
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }

        try {
            new Div(new Number(1), new Add(new Number(2), new Variable("x"))).eval("x=-2");
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }
}
