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
            if (isPrime(j)) {
                return true;
            }
        }
        return false;
    }

    public static boolean parallelCheck(List<Integer> numbers, int workingThreadsCount) {
        List<Integer> synchronizedList = Collections.synchronizedList(numbers);
        Thread[] threads = new Thread[workingThreadsCount];
        final AtomicBoolean result = new AtomicBoolean(false);

        for (int i = 0; i < workingThreadsCount; i++) {
            threads[i] = new Thread(() -> {
                while (!synchronizedList.isEmpty() && !result.get()) {
                    result.compareAndExchange(true, isPrime(synchronizedList.getLast()));
                }
            });
        }

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println("Prime numbers check was interrupted: " + e.getMessage());
            }
        }
        return result.get();
    }

    public boolean parallelStreamCheck(List<Integer> numbers) {
        return numbers.parallelStream().anyMatch(PrimesFinder::isPrime);
    }
}
