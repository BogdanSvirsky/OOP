import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

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