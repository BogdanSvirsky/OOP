import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CompositeNumberFinderTest {
    @Test
    void isCompositeTest() {
        assertFalse(CompositeNumberFinder.isComposite(2));
        assertFalse(CompositeNumberFinder.isComposite(3));
        assertFalse(CompositeNumberFinder.isComposite(6373));
        assertFalse(CompositeNumberFinder.isComposite(10477));
        assertFalse(CompositeNumberFinder.isComposite(500009));
        assertFalse(CompositeNumberFinder.isComposite(5000011));
        assertFalse(CompositeNumberFinder.isComposite(500000003));
        assertTrue(CompositeNumberFinder.isComposite(1073741824));
        assertTrue(CompositeNumberFinder.isComposite(1073741824));
        assertTrue(CompositeNumberFinder.isComposite(387420489));
        assertTrue(CompositeNumberFinder.isComposite(11723776));
        assertTrue(CompositeNumberFinder.isComposite(614656));
        assertTrue(CompositeNumberFinder.isComposite(14641));
        assertTrue(CompositeNumberFinder.isComposite(4));
    }

    static void testFind(CompositeNumberFinder finder) {
        Random random = new Random();
        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i = 0; i < 1000; i++) {
            Collections.addAll(arrayList, 2, 3, 5, 7, 11, 101, 131, 151, 181, 191, 313,
                    353, 373, 383, 727, 757, 787, 797, 919, 929, 10301, 10501, 10601, 1000003,
                    2000423, 5000981, 7503077, 60001199);
        }
        assertFalse(finder.find(arrayList));
        for (int i = 4; i < 1000; i++) {
            arrayList.add(random.nextInt(10000) * random.nextInt(100000));
        }
        for (int i = 0; i < 1000; i++) {
            Collections.addAll(arrayList, 2, 3, 5, 7, 11, 101, 131, 151, 181, 191, 313,
                    353, 373, 383, 727, 757, 787, 797, 919, 929, 10301, 10501, 10601, 1000003,
                    2000423, 5000981, 7503077, 60001199);
        }
        assertTrue(finder.find(arrayList));
    }
}