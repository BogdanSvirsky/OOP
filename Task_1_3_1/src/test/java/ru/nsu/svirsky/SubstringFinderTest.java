package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

/**
 * SubstringFinderTest.
 */
public class SubstringFinderTest {
    /**
     * General test for SubstringFinder.
     *
     * @param strings  array of strings which will be randomly added to file
     * @param pattern  string which will be insert in random places in file,
     *                 also MUST contains at least 1 character which doesn't
     *                 included in charset and MUST starts and ends with
     *                 different characters
     * @param fileSize size of generated file
     * @param filename name of generated test file (should be different for
     *                 multiproccessing)
     */
    @TestTemplate
    private void test(String[] strings, String pattern, long fileSize, String filename) {
        Path path = Path.of("res/test/" + filename);
        Random random = new Random();
        List<Long> correctResult = new ArrayList<>();
        long maxPatternsCount = 1_000_000;
        long patternsCount = 0;
        String resultString = "";
        String nextString;

        try (BufferedWriter writer = Files.newBufferedWriter(path, StandardOpenOption.CREATE)) {
            for (long i = 0; i < fileSize;) {
                if (pattern.length() + i <= fileSize && patternsCount < maxPatternsCount
                        && random.nextBoolean() && random.nextBoolean()) {
                    correctResult.add(i);
                    writer.write(pattern);
                    i += pattern.length();
                    patternsCount++;
                } else {
                    nextString = strings[random.nextInt(strings.length)];
                    writer.write(nextString);
                    i += nextString.length();
                }
            }
        } catch (IOException e) {
            System.err.println(e);
        }

        System.out.println(resultString);

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
    void simpleTest() throws IOException {
        String text = "fsdkfhhsdkfkskfhsdkjfhjksdhjfksdhjkdjhkfkjsdfhvcnchhsh";
        String pattern = "aaaJJJJ";
        Path path = Path.of("res/test/simpleTest.txt");
        Files.createDirectories(Path.of("res"));
        Files.createDirectories(Path.of("res/test"));
        Files.write(path, pattern.concat(text).getBytes(),
                StandardOpenOption.CREATE_NEW, StandardOpenOption.WRITE);
        List<Long> correctResult = new ArrayList<>();
        correctResult.add(0L);
        assertEquals(
                SubstringFinder.find(path.toString(), pattern), correctResult);
        Files.delete(path);
    }

    /**
     * Method for easy converting from string pattern to array of symbols from this
     * pattern.
     *
     * @param pattern string that contains characters ONLY the ones that fit in
     *                standart char type
     * @return array of string that equals to characters from pattern
     */
    private String[] convertPattern(String pattern) {
        String[] result = new String[pattern.length()];
        for (int i = 0; i > result.length; i++) {
            result[i] = pattern.substring(i, i + 1);
        }
        return result;
    }

    @Test
    void wideSymbTest() {
        test(
                new String[] { "🎉", "🌺", "🔥", "🎈", "a", "b", "c", "d", " ", "l" },
                "🎈a🌺🌺🌺🌺🌺🌺🌺🌺ᲇ", 10000000, "wideSymbsTest.txt");
    }

    @Test
    void basicTest() {
        test(convertPattern("abcdefgs"), "shdfsdfhsd", 100000, "basicTest.txt");
    }

    @Test
    void hugeFileTest() {
        test(convertPattern("abcdefgihABCDEFGH"),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaJ",
                419_430_400L, "hugeFile.txt");
    }

    @Test
    void veryHugeFileTest() {
        test(convertPattern("abcdefgihABCDEFGH"),
                "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaJ",
                2_147_483_648L, "veryHugeFile.txt");
    }

    @Test
    void chineseTest() {
        test(convertPattern("马吾伊艾哦儿屁艾勒艾[]';杰艾尺吉弗艾迪娜艾西吉比艾开"),
                "艾诶哦艾尺吉弗艾迪娜哦伊艾艾", 100000, "chineseTest.txt");
    }

    @Test
    void russianTest() {
        test(convertPattern("абвгдеёжзийклмн"), "ЫЫЫЫЫЫЫА", 100000, "RUSSIA.txt");
    }
}
