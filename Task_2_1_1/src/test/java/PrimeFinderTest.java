import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class PrimeFinderTest {
    @Test
    void basicTest() {
        assertTrue(PrimesFinder.isPrime(2));
        assertTrue(PrimesFinder.isPrime(3));
        assertTrue(PrimesFinder.isPrime(6373));
        assertTrue(PrimesFinder.isPrime(10477));
        assertTrue(PrimesFinder.isPrime(500009));
        assertTrue(PrimesFinder.isPrime(5000011));
        assertTrue(PrimesFinder.isPrime(500000003));
        assertFalse(PrimesFinder.isPrime(1073741824));
        assertFalse(PrimesFinder.isPrime(1073741824));
        assertFalse(PrimesFinder.isPrime(387420489));
        assertFalse(PrimesFinder.isPrime(11723776));
        assertFalse(PrimesFinder.isPrime(614656));
        assertFalse(PrimesFinder.isPrime(14641));
        assertFalse(PrimesFinder.isPrime(4));
    }

    @Test
    void sequentialTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 4; i < 100; i++) {
            arrayList.add(i * i * i * i);
        }
        assertFalse(PrimesFinder.sequentialCheck(arrayList));
        Collections.addAll(arrayList, 2, 3, 5, 7, 11, 101, 131, 151, 181, 191, 313, 353, 373, 383, 727, 757, 787, 797, 919, 929, 10301, 10501, 10601);
        assertTrue(PrimesFinder.sequentialCheck(arrayList));
    }

    @Test
    void parallelTest() {
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 4; i < 100; i++) {
            arrayList.add(i * i * i * i);
        }
        assertFalse(PrimesFinder.sequentialCheck(arrayList));
        Collections.addAll(arrayList, 2, 3, 5, 7, 11, 101, 131, 151, 181, 191, 313, 353, 373, 383, 727, 757, 787, 797, 919, 929, 10301, 10501, 10601);
        assertTrue(PrimesFinder.parallelCheck(arrayList, 4));
    }
}