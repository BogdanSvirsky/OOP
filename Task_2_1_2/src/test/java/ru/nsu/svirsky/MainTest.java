package ru.nsu.svirsky;

import java.net.InetAddress;
import java.net.UnknownHostException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MainTest {
    @Test
    public void testWithCompositeNumbers() throws InterruptedException, UnknownHostException {
        int[] inputArray = {2, 3, 5, 6, 7};
        Job job = new Job(inputArray);
        int port = 12345;

        new Thread(() -> new Coordinator(port, null, job)).start();

        new Worker(InetAddress.getLocalHost(), port);

        assertTrue(job.getResult());
    }

    @Test
    public void testWithoutCompositeNumbers() throws Exception {
        int[] inputArray = {2, 3, 5, 7, 11};
        Job job = new Job(inputArray);
        int port = 12346;

        new Coordinator(port, null, job);

        new Worker(InetAddress.getLocalHost(), port);

        assertFalse(job.getResult());
    }
}
