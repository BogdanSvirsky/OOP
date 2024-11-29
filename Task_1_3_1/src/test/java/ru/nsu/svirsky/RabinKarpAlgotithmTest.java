package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class RabinKarpAlgotithmTest {
    @Test
    void test() {
        String stringWithoutPattern = "oiopiioinbonibonboinio";
        String pattern = "abcdefgf";
        RabinKarpAlgorithm algorithm = new RabinKarpAlgorithm(pattern);

        for (int i = 0; i < stringWithoutPattern.length(); i++) {
            algorithm.addStringCharacter(stringWithoutPattern.charAt(i));
            assertFalse(algorithm.isSubstringFounded());
        }

        for (int i = 0; i < pattern.length(); i++) {
            algorithm.addStringCharacter(pattern.charAt(i));
        }

        assertTrue(algorithm.isSubstringFounded());
    }
}
