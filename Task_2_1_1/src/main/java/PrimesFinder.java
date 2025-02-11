import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class PrimesFinder {
    public static boolean isPrime(int num) {
        for (int i = 2; i <= Math.sqrt(num); i++) {
            if (num % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static boolean sequentialCheck(List<Integer> numbers) {
        for (int j : numbers) {
            if (!isPrime(j)) {
                return true;
            }
        }
        return false;
    }

    public static boolean parallelCheck(List<Integer> numbers, int workingThreadsCount) throws InterruptedException {
        Thread[] threads = new Thread[workingThreadsCount];
        final boolean[] result = {false};

        for (int i = 0; i < workingThreadsCount; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                int countProcessedNumber = 0;
                while (threadNumber + countProcessedNumber * workingThreadsCount < numbers.size()) {
                    if (!isPrime(numbers.get(threadNumber + countProcessedNumber * workingThreadsCount))) {
                        synchronized (result) {
                            result[0] = true;
                        }
                        break;
                    }
                    countProcessedNumber++;
                }
            });
        }

        for (Thread thread : threads) {
            thread.join();
        }

        return result[0];
    }

    public static boolean parallelStreamCheck(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(x -> !isPrime(x));
    }
}
