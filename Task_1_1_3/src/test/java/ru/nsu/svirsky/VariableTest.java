package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class VariableTest {
    @Test
    void basicTest() {
        Variable var = new Variable("abaAba");

        assertEquals(var.toString(), "abaAba");

        assertEquals(var.eval("abaAba=1"), 1);

        assertTrue(var.derivative("abaAba") instanceof Number);
        assertEquals(var.derivative("abaAba").eval(""), 1);
        assertEquals(var.derivative("abaAb").eval(""), 0);

        assertTrue(var.hasVariables());

        assertTrue(var.simplify() instanceof Variable);
        assertEquals(var.simplify().eval("abaAba=1"), var.eval("abaAba=1"));
    }
}
