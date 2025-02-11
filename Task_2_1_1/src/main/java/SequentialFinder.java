import java.util.List;

public class SequentialFinder extends CompositeNumberFinder {
    @Override
    public boolean find(List<Integer> numbers) {
        for (int j : numbers) {
            if (isComposite(j)) {
                return true;
            }
        }
        return false;
    }
}
