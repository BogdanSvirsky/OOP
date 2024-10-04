package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for subtraction.
 * 
 * @author Svirsky Bogdan
 */
public class SubTest {
    @Test
    void basicTest() {
        assertTrue(new Sub(new Variable("x"), new Number(1)).hasVariables());
        assertTrue(new Sub(new Number(1), new Variable("x")).hasVariables());

        assertEquals(new Sub(new Variable("x"), new Number(1)).toString(), "(x - 1.0)");

        assertEquals(
                new Sub(new Variable("x"), new Number(1)).eval("x=1"), 0);
        assertEquals(
                new Sub(new Number(6), new Variable("x")).eval("x=2"), 4);

        assertEquals(
                new Sub(new Number(1), new Variable("x")).derivative("x").eval(""), -1);
    }

    @Test
    void derivativeTest() {
        assertEquals(
                new Sub(new Number(1), new Variable("x")).derivative("x").eval(""), -1);

        Expression diminutive = new Sub(new Number(5), new Variable("x"));
        Expression divider = new Div(new Variable("x"), new Number(10));

        assertEquals(
                new Sub(diminutive, divider).derivative("x").eval("x=1"),
                new Sub(diminutive.derivative("x"), divider.derivative("x")).eval("x=1"));
    }

    @Test
    void simplifyTest() {
        Sub subConstants = new Sub(new Number(5), new Number(1));

        assertTrue(subConstants.simplify() instanceof Number);
        assertEquals(subConstants.simplify().eval(""), 4);

        Sub subWithVars = new Sub(new Variable("x"), subConstants);

        assertTrue(subWithVars.simplify() instanceof Sub);
        assertEquals(subWithVars.simplify().eval("x=2"), subWithVars.eval("x=2"));

        subWithVars = new Sub(subConstants, new Variable("x"));

        assertTrue(subWithVars.simplify() instanceof Sub);
        assertEquals(subWithVars.simplify().eval("x=2"), subWithVars.eval("x=2"));

        subWithVars = (Sub) subWithVars.simplify();

        assertTrue(subWithVars.simplify() instanceof Sub);
        assertEquals(subWithVars.simplify().eval("x=2"), subWithVars.eval("x=2"));
    }
}
