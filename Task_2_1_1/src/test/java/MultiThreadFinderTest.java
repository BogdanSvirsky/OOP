import org.junit.jupiter.api.Test;

class MultiThreadFinderTest {
    @Test
    void multiThreadFindTest() {
        CompositeNumberFinderTest.testFind(new MultiThreadFinder(8));
    }
}