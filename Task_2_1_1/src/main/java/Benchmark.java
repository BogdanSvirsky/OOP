import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Benchmark {
    public static void run(CompositeNumberFinder finder) {
        System.out.println("Run benchmarks for: " + finder);
        ArrayList<Integer> arrayList = new ArrayList<>();
        List<Integer> primeNums = Arrays.asList(2, 3, 5, 7, 11, 101, 131, 151, 181, 191, 313, 353, 373, 383, 727,
                757, 787, 797, 919, 929, 10301, 10501, 10601, 1000003, 2000423, 5000981, 7503077, 60001199);

        final int countTries = 3;

        for (int i = 10; i <= 1000000; i *= 10) {
            for (int j = 0; j < (i - i / 10); j++) {
                arrayList.addAll(primeNums);
            }

            long resultTime = 0, startTime, endTime;

            for (int j = 0; j < countTries; j++) {
                startTime = System.currentTimeMillis();
                finder.find(arrayList);
                endTime = System.currentTimeMillis();
                resultTime += endTime - startTime;
            }

            System.out.printf(
                    "Calculation time for %d prime numbers: %d ms\n", primeNums.size() * i, resultTime / countTries);
            System.out.flush();
        }
    }

    public static void main(String[] args) {
        run(new SequentialFinder());

        run(new ParallelStreamFinder());

        // Benchmarks for multi-thread implementation
        run(new MultiThreadFinder(4));

        run(new MultiThreadFinder(Runtime.getRuntime().availableProcessors()));

        run(new MultiThreadFinder(100000));
    }
}
