package ru.nsu.svirsky;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RabinKarpAlgorithm {
    private Queue<Character> currentCharacters = new LinkedBlockingQueue<>();
    private String substring;
    private int P = 17;
    private int MODULE = 999999937;
    private final int substringHash;
    private int currentStringHash = 0;

    public RabinKarpAlgorithm(String substring) {
        this.substring = substring;
        int substringHash = 0;
        int powerOfP = 1;

        for (int i = 0; i < substring.length(); i++) {
            substringHash += (powerOfP * substring.charAt(i)) % MODULE;
            powerOfP = (powerOfP * P) % MODULE;
        }

        this.substringHash = substringHash;
    }

    public void addStringCharacter(char symb) {
        if (currentCharacters.size() == substring.length()) {
            currentStringHash -= currentCharacters.poll();
            currentStringHash /= P;
        }

        currentCharacters.add(symb);
        int powerOfP = 1;
        for (int i = 0; i < currentCharacters.size(); i++) {
            powerOfP = (powerOfP * P) % MODULE;
        }
        currentStringHash += (powerOfP * symb) % MODULE;
    }

    public boolean isSubstringFounded() {
        if (currentStringHash == substringHash && currentCharacters.size() == substring.length()) {
            int index = 0;
            for (Character stringChar : currentCharacters) {
                if (stringChar.charValue() != substring.charAt(index)) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }
}
