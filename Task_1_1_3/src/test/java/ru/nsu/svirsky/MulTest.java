package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class MulTest {
    @Test
    void basicTest() {
        assertTrue(new Mul(new Variable("x"), new Number(1)).hasVariables());
        assertTrue(new Mul(new Number(1), new Variable("x")).hasVariables());

        assertEquals(new Mul(new Variable("x"), new Number(1)).toString(), "(x * 1.0)");

        assertEquals(
                new Mul(new Variable("x"), new Number(1)).eval("x=1"), 1);
        assertEquals(
                new Mul(new Number(6), new Variable("x")).eval("x=2"), 12);
    }

    @Test
    void derivativeTest() {
        assertEquals(new Mul(new Number(5), new Variable("x")).derivative("x").eval(""), 5);

        Expression firstMultiplier = new Add(new Number(5), new Variable("x"));
        Expression secondMultiplier = new Div(new Variable("x"), new Number(10));

        assertEquals(
                new Mul(firstMultiplier, secondMultiplier).derivative("x").eval("x=1"),
                new Add(
                        new Mul(firstMultiplier.derivative("x"), secondMultiplier),
                        new Mul(firstMultiplier, secondMultiplier.derivative("x"))).eval("x=1"));
    }

    @Test
    void simplifyTest() {
        Expression firstMultiplier = new Number(1);
        Expression secondMultiplier = new Number(0);
        Expression var = new Variable("x");

        assertTrue(new Mul(secondMultiplier, var).simplify() instanceof Number);
        assertEquals(new Mul(secondMultiplier, var).simplify().eval(""), 0);

        assertTrue(new Mul(var, secondMultiplier).simplify() instanceof Number);
        assertEquals(new Mul(var, secondMultiplier).simplify().eval(""), 0);

        assertTrue(new Mul(firstMultiplier, var).simplify() instanceof Variable);
        assertEquals(new Mul(firstMultiplier, var).simplify().eval("x=1"), 1);

        assertTrue(new Mul(var, firstMultiplier).simplify() instanceof Variable);
        assertEquals(new Mul(var, firstMultiplier).simplify().eval("x=1"), 1);
    }
}
