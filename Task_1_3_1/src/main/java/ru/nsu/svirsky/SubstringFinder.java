package ru.nsu.svirsky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.CharBuffer;
import java.util.ArrayList;
import java.util.List;

public class SubstringFinder {
    private static final int bufferSize = 1000;
    private static CharBuffer charBuffer = CharBuffer.allocate(bufferSize);

    public static List<Integer> find(String filename, String substring) throws FileNotFoundException, IOException {
        ArrayList<Integer> result = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

        RabinKarpAlgorithm algorithm = new RabinKarpAlgorithm(substring);

        int globalIndex = 0;

        while (bufferedReader.ready()) {
            bufferedReader.read(charBuffer);

            for (int i = 0; i < bufferSize; i++) {
                algorithm.addStringCharacter(charBuffer.get(i));
                if (algorithm.isSubstringFounded()) {
                    result.add(globalIndex + i);
                }
            }

            globalIndex += 1000;
            charBuffer.clear();
        }

        bufferedReader.close();

        return result;
    }
}
