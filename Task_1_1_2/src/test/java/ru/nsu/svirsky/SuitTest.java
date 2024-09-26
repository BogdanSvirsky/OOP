package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.enums.Suit;

public class SuitTest {
    @Test
    void mainTest() {
        assertEquals(Suit.HEARTS.name, "Черви");
        assertEquals(Suit.DIAMONDS.name, "Буби");
        assertEquals(Suit.SPADES.name, "Пики");
        assertEquals(Suit.CLUBS.name, "Крести");
    }
}
