package ru.nsu.svirsky;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Module to find a substring in a file.
 */
public class SubstringFinder {
    public static List<Long> find(String filename, String substring) throws FileNotFoundException, IOException {
        ArrayList<Long> result = new ArrayList<>();

        BufferedReader bufferedReader = new BufferedReader(new FileReader(filename));

        RabinKarpAlgorithm algorithm = new RabinKarpAlgorithm(substring);

        long index = 0;

        while (bufferedReader.ready()) {
            for (char c : Character.toChars(bufferedReader.read())) {
                algorithm.addStringCharacter(c);
                if (algorithm.isSubstringFounded()) {
                    result.add(index - substring.length() + 1);
                }
                index++;
            }
        }

        bufferedReader.close();

        return result;
    }
}
