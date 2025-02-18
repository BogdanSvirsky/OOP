import java.util.List;

/**
 * Abstract class for implementation of finder.
 *
 * @author BogdanSvirsky
 */
public abstract class CompositeNumberFinder {
    static boolean isComposite(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    abstract boolean find(List<Integer> numbers);
}
