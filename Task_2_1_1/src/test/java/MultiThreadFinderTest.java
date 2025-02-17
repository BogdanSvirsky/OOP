import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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