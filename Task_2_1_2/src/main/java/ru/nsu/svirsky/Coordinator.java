package ru.nsu.svirsky;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class Coordinator {
    public static int CHUNK_SIZE = 5;
    private static final int CONNECTIONS_LIMIT = 10;
    private final List<Connection> connections = new ArrayList<>();
    private boolean isRunning = true;
    private final ServerSocket listenSocket;
    private final Map<Connection, Task> taskMap = new HashMap<>();
    private TaskPool taskPool;
    private Job currentJob;


    public Coordinator(int port, InetAddress inetAddress, Job job) {
        this.currentJob = job;
        this.taskPool = new TaskPool(job.inputArray, CHUNK_SIZE);
        try {
            if (inetAddress == null) {
                listenSocket = new ServerSocket(port, CONNECTIONS_LIMIT);
            } else {
                listenSocket = new ServerSocket(port, CONNECTIONS_LIMIT, inetAddress);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        Thread executor = new Thread(() -> {
            try {
                lifeloop();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        executor.start();
    }

    void catchConnections() throws IOException {
        while (true) {
            try {
                Socket newConnSocket = listenSocket.accept();
                synchronized (connections) {
                    connections.add(new Connection(newConnSocket));
                }
            } catch (SocketException ignored) {
                break;
            }
        }
    }

    void lifeloop() throws IOException {
        Thread connsCatcher = new Thread(() -> {
            try {
                catchConnections();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
        connsCatcher.start();

        while (!taskPool.isCompleted()) {
            synchronized (connections) {
                for (Connection conn : connections) {
                    if (taskMap.containsKey(conn)) {
                        if (conn.inputStream.available() == 0) {
                            continue;
                        }
                        try {
                            taskMap.get(conn).setResult(conn.inputStream.readBoolean());
                            taskMap.remove(conn);
                        } catch (SocketTimeoutException ignored) {
                        }
                    } else {
                        Task task = taskPool.getTask();
                        if (task == null) {
                            continue;
                        }

                        int[] array = task.getData();
                        conn.outputStream.writeInt(task.getData().length);
                        for (int j : array) {
                            conn.outputStream.writeInt(j);
                        }
                        taskMap.put(conn, task);
                    }
                }
            }
        }

        currentJob.setResult(taskPool.getResult());
        connsCatcher.interrupt();

        for (Connection conn : connections) {
            conn.outputStream.writeInt(-1);
            conn.close();
        }
        listenSocket.close();
    }
}