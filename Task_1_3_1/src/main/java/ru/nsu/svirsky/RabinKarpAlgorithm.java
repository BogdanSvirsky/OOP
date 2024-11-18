package ru.nsu.svirsky;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Implementation of Rabin-Karp algorithm.
 */
public class RabinKarpAlgorithm {
    private Queue<Character> currentCharacters = new LinkedBlockingQueue<>();
    private String substring;
    private long p = 3;
    private final long substringHash;
    private long currentStringHash = 0;
    private long powerOfP = 1;
    private static final long MODULE = 1_000_000_007;
    private static boolean isInversedPCalculated = false;
    private static long inversedP;

    public RabinKarpAlgorithm(String substring) {
        this.substring = substring;
        long substringHash = 0;
        long powerOfP = 1;

        for (int i = 0; i < substring.length(); i++) {
            substringHash = module(substringHash + module(powerOfP * substring.charAt(i)));
            powerOfP = module(powerOfP * p);
        }

        this.substringHash = substringHash;
    }

    public void addStringCharacter(char symb) {
        if (currentCharacters.size() == substring.length()) {
            currentStringHash = module(currentStringHash - currentCharacters.remove());
            if (!isInversedPCalculated) {
                inversedP = inv(p);
                isInversedPCalculated = true;
            }
            currentStringHash = module(currentStringHash * inversedP);
            currentStringHash = module(currentStringHash + module(symb * powerOfP));
        } else {
            currentStringHash = module(currentStringHash + module(powerOfP * symb));
            if (currentCharacters.size() + 1 != substring.length()) {
                powerOfP = module(powerOfP * p);
            }
        }
        currentCharacters.add(symb);
    }

    public boolean isSubstringFounded() {
        if (currentStringHash == substringHash && currentCharacters.size() == substring.length()) {
            int index = 0;
            for (Character stringChar : currentCharacters) {
                if (stringChar.charValue() != substring.charAt(index++)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    private static long module(long n) {
        if (n < 0) {
            return n % MODULE + MODULE;
        } else {
            return n % MODULE;
        }
    }

    private static long inv(long n) {
        long res;
        long power;
        res = 1;
        power = 1;
        if (n == 0) {
            return -1;
        }
        for (int j = 1; j < 64; j++) {
            if (((module(-1) - 1) & power) != 0) {
                res *= n;
                res = module(res);
            }
            power <<= 1;
            n *= n;
            n = module(n);
        }
        return module(res);
    }
}
