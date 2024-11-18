package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;

/*
 * SubstringFinderTest.
 */
public class SubstringFinderTest {
    /**
     * General test for SubstringFinder.
     *
     * @param characters set of characters which will be randomly added to file
     * @param pattern    string which will be insert in random places in file,
     *                   also MUST contains at least 1 character which doesn't
     *                   included in charset and MUST starts and ends with
     *                   different characters
     * @param fileSize   size of generated file
     */
    public void test(String characters, String pattern, long fileSize, String filename) {
        Path path = Path.of("res/test/" + filename);
        Random random = new Random();
        List<Long> correctResult = new ArrayList<>();
        long MAXIMUM_PATTERNS_COUNT = 1_000_000;
        long patternsCount = 0;

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            for (long i = 0; i < fileSize; i++) {
                if (pattern.length() + i <= fileSize && patternsCount < MAXIMUM_PATTERNS_COUNT
                        && random.nextBoolean() && random.nextBoolean()) {
                    correctResult.add(i);
                    writer.write(pattern);
                    i += pattern.length() - 1;
                    patternsCount++;
                } else {
                    writer.write(characters.charAt(random.nextInt(characters.length())));
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        List<Long> result;
        try {
            result = SubstringFinder.find(path.toString(), pattern);
        } catch (FileNotFoundException e) {
            System.err.println("File doesn't exist! " + e);
            return;
        } catch (IOException e) {
            System.err.println(e);
            return;
        }

        try {
            Files.delete(path);
        } catch (IOException e) {
            System.err.println(e);
        }

        assertEquals(correctResult, result);
    }

    @Test
    void basicTest() {
        test("abcdefgs", "shdfsdfhsd", 100000, "basicTest.txt");
    }

    @Test
    void hugeFileTest() {
        test("abcdefgihABCDEFGH",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaJ",
                419_430_400l, "hugeFile.txt");
    }

    @Test
    void veryHugeFileTest() {
        test("abcdefgihABCDEFGH",
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaJ",
                2_147_483_648l, "veryHugeFile.txt");
    }

    @Test
    void chineseTest() {
        test("马吾伊艾哦儿屁艾勒艾[]';杰艾尺吉弗艾迪娜艾西吉比艾开",
                "艾诶哦艾尺吉弗艾迪娜哦伊艾艾", 100000, "chineseTest.txt");
    }
}
