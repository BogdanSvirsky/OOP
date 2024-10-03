package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;


public class NumberTest {
    @Test
    void basicTest() {
        Number number = new Number(2);

        assertEquals(number.eval(""), 2);

        assertTrue(number.derivative("") instanceof Number);
        assertEquals(number.derivative("").eval(""), 0);

        assertTrue(number.simplify() instanceof Number);
        assertEquals(number.simplify().eval(""), 2);

        assertEquals(number.toString(), "2.0");

        assertFalse(number.hasVariables());
    }
}
