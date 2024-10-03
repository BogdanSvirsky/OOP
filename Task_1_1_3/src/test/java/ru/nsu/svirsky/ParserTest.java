package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import java.util.Random;

public class ParserTest {
    @Test
    void basicTest() {
        Expression e = Parser.parse("((2 - x) / (1 + x)) + ((30 - y) * (1312 + y))");
        e.print();
        int x;
        int y;
        Random random = new Random();

        for (int i = 0; i < 100000; i++) {
            x = random.nextInt(10000);
            y = random.nextInt(10000);
            if (1 + x == 0) {
                continue;
            }
            assertEquals(
                    (e.eval(String.format("x=%d;y=%d", x, y))),
                    (((2.0 - x) / (1.0 + x)) + ((30 - y) * (1312 + y))));

        }

        assertEquals(Parser.parse("2.25").eval(""), 2.25);
        assertEquals(Parser.parse("1 + (-2.25)").eval(""), -1.25);
    }

    @Test
    void exceptionTest() {
        try {
            Parser.parse("(1/0)").eval("");
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }

        try {
            Parser.parse("(1/(2 - x))").eval("x=2");
        } catch (ArithmeticException e) {
            assertTrue(true);
            return;
        }

        assertTrue(false);
    }
}
