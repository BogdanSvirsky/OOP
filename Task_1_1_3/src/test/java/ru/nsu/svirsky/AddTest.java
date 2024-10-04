package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

/**
 * Tests for adding.
 * 
 * @author Svirsky Bogdan
 */
public class AddTest {
    @Test
    void basicTest() {
        assertTrue(new Add(new Variable("x"), new Number(1)).hasVariables());
        assertTrue(new Add(new Number(1), new Variable("x")).hasVariables());

        assertEquals(new Add(new Variable("x"), new Number(1)).toString(), "(x + 1.0)");

        assertEquals(
                new Add(new Variable("x"), new Number(1)).eval("x=1"), 2);
        assertEquals(
                new Add(new Number(6), new Variable("x")).eval("x=2"), 8);
    }

    @Test
    void derivativeTest() {
        assertEquals(
            new Add(new Number(1), new Variable("x")).derivative("x").eval(""), 1);

        Expression firstSummand = new Add(new Number(5), new Variable("x"));
        Expression secondSummand = new Div(new Variable("x"), new Number(10));

        assertEquals(
                new Add(firstSummand, secondSummand).derivative("x").eval("x=1"),
                new Add(firstSummand.derivative("x"), secondSummand.derivative("x")).eval("x=1"));
    }

    @Test
    void simplifyTest() {
        Add addingConstants = new Add(new Number(5), new Number(1));

        assertTrue(addingConstants.simplify() instanceof Number);
        assertEquals(addingConstants.simplify().eval(""), 6);

        Add addingWithVars = new Add(new Variable("x"), addingConstants);

        assertTrue(addingWithVars.simplify() instanceof Add);
        assertEquals(addingWithVars.simplify().eval("x=2"), addingWithVars.eval("x=2"));

        addingWithVars = new Add(addingConstants, new Variable("x"));

        assertTrue(addingWithVars.simplify() instanceof Add);
        assertEquals(addingWithVars.simplify().eval("x=2"), addingWithVars.eval("x=2"));

        addingWithVars = (Add) addingWithVars.simplify();

        assertTrue(addingWithVars.simplify() instanceof Add);
        assertEquals(addingWithVars.simplify().eval("x=2"), addingWithVars.eval("x=2"));
    }
}
