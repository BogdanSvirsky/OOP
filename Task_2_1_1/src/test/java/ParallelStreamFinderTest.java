import org.junit.jupiter.api.Test;

class ParallelStreamFinderTest {
    @Test
    void parallelStreamFindTest() {
        CompositeNumberFinderTest.testFind(new ParallelStreamFinder());
    }
}