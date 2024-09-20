package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

public class BlackjackTest {
    @Test
    void stupidTest() {
        // String testInput = "0\n";
        // for (int i = 0; i < 100; i++) {
        //     testInput += "0\n";
        // }

        // InputStream inputStream = ;
        int testsCount = 1;
        System.setIn(new ByteArrayInputStream("0\n".repeat(testsCount).getBytes()));

        for (int i = 0; i < testsCount; i++) {
            Blackjack.playRound();
        }

        assertTrue(true);
    }
}
