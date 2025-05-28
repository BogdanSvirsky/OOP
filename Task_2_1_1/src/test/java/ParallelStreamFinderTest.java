import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Tests for parallel stream API finder implementation.
 *
 * @author BogdanSvirsky
 */
class ParallelStreamFinderTest {
    @Test
    void parallelStreamFindTest() {
        CompositeNumberFinderTest.testFind(new ParallelStreamFinder());
    }

    @Test
    void toStringTest() {
        assertEquals(new ParallelStreamFinder().toString(),
                "Parallel Stream search for composite numbers");
    }
}