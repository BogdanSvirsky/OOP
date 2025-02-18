import java.util.List;

/**
 * Implementation of composite number finder by parallel stream API.
 *
 * @author BogdanSvirsky
 */
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
