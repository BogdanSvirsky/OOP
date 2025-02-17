
import java.util.List;

public class ParallelStreamFinder extends CompositeNumberFinder {
    @Override
    boolean find(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(CompositeNumberFinder::isComposite);
    }

    @Override
    public String toString() {
        return "Parallel Stream search for composite numbers";
    }
}
