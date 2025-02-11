import java.util.List;

public class MultiThreadFinder extends CompositeNumberFinder {
    private final int workingThreadsCount;

    public MultiThreadFinder(int workingThreadsCount) {
        this.workingThreadsCount = workingThreadsCount;
    }

    @Override
    boolean find(List<Integer> numbers) {
        Thread[] threads = new Thread[workingThreadsCount];
        final boolean[] result = {false};

        for (int i = 0; i < workingThreadsCount; i++) {
            final int threadNumber = i;
            threads[i] = new Thread(() -> {
                int countProcessedNumbers = 0;
                while (threadNumber + countProcessedNumbers * workingThreadsCount < numbers.size() && !result[0]) {
                    if (isComposite(numbers.get(threadNumber + countProcessedNumbers * workingThreadsCount))) {
                        synchronized (result) {
                            result[0] = true;
                        }
                        break;
                    }
                    countProcessedNumbers++;
                }
            });
            threads[i].start();
        }

        boolean isSomeoneInterrupted = false;

        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                System.err.println(e.getMessage());
                isSomeoneInterrupted = true;
            }
        }

        while (isSomeoneInterrupted) {
            for (Thread thread : threads) {
                if (thread.isInterrupted()) {
                    thread.start();
                }
            }
            isSomeoneInterrupted = false;
            for (Thread thread : threads) {
                try {
                    thread.join();
                } catch (InterruptedException e) {
                    isSomeoneInterrupted = true;
                }
            }
        }

        return result[0];
    }
}
