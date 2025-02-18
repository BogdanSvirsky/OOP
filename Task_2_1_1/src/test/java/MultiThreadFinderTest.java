import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

/**
 * Tests for multi-thread finder implementation.
 *
 * @author BogdanSvirsky
 */
class MultiThreadFinderTest {
    @Test
    void multiThreadFindTest() {
        CompositeNumberFinderTest.testFind(new MultiThreadFinder(8));
    }

    @Test
    void toStringTest() {
        assertEquals(new MultiThreadFinder(4).toString(),
                "Multi-thread search for composite number, number of threads - 4");
    }
}