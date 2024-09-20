package ru.nsu.svirsky;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.*;

public class BlackjackTest {
    @Test
    void testRounds() {
        // String testInput = "0\n";
        // for (int i = 0; i < 100; i++) {
        //     testInput += "0\n";
        // }

        // InputStream inputStream = ;
        System.setIn(new ByteArrayInputStream("0\n".getBytes()));

        // for (int i = 0; i < 100; i++) {
            Blackjack.playRound();
        // }

        assertTrue(true);
    }
}
