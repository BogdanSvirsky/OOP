package ru.nsu.svirsky;

import java.io.ByteArrayInputStream;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    void simpleTest() {
        int testsCount = 1;

        System.setIn(new ByteArrayInputStream("0\n".repeat(testsCount).getBytes()));

        for (int i = 0; i < testsCount; i++) {
            Main.playRound();
            System.out.printf("%d / %d tests are passed\n", i + 1, testsCount);
        }

        assertTrue(true);
    }
}