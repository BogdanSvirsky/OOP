package ru.nsu.svirsky;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class Main {
    public static void main(String[] args) throws UnknownHostException, InterruptedException {
        Job job = new Job(new int[] {2, 3, 5, 7, 11, 12});
        Coordinator coordinator = new Coordinator(8080, null, job);
        Worker worker = new Worker(InetAddress.getLocalHost(), 8080);
        System.out.println("result : " + job.getResult());
    }
}
