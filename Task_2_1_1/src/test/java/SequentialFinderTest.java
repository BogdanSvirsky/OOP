import org.junit.jupiter.api.Test;

class SequentialFinderTest {
    @Test
    void sequentialFindTest() {
        CompositeNumberFinderTest.testFind(new SequentialFinder());
    }
}