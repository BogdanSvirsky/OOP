package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import ru.nsu.svirsky.enums.Rank;

public class RankTest {
    @Test
    void mainTest() {
        assertEquals(Rank.ACE.name, "Туз");
        assertEquals(Rank.ACE.value, 11);
        assertEquals(Rank.TWO.name, "Двойка");
        assertEquals(Rank.TWO.value, 2);
        assertEquals(Rank.THREE.name, "Тройка");
        assertEquals(Rank.THREE.value, 3);
    }
}
