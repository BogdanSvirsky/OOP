import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Test for simple sequential finder.
 *
 * @author BogdanSvirsky
 */
class SequentialFinderTest {
    @Test
    void sequentialFindTest() {
        CompositeNumberFinderTest.testFind(new SequentialFinder());
    }

    @Test
    void toStringTest() {
        assertEquals(new SequentialFinder().toString(),
                "Sequential search for composite numbers");
    }
}