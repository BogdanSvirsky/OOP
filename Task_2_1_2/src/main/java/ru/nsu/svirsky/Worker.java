package ru.nsu.svirsky;

import java.io.IOException;
import java.net.InetAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Worker {
    private final Connection connection;

    private boolean isComposite(int number) {
        for (int i = 2; i <= Math.sqrt(number); i++) {
            if (number % i == 0) {
                return true;
            }
        }
        return false;
    }

    public Worker(InetAddress inetAddress, int port) {
        try {
            this.connection = new Connection(new Socket(inetAddress, port));
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

    private void lifeloop() throws IOException {
        List<Integer> currChunk = new ArrayList<>();

        while (true) {
            if (connection.inputStream.available() == 0) {
                continue;
            }
            int size = connection.inputStream.readInt();
            if (size == -1) {
                break;
            }
            for (int i = 0; i < size; i++) {
                currChunk.add(connection.inputStream.readInt());
            }

            boolean isCompositeFound = false;
            for (int num : currChunk) {
                if (isComposite(num)) {
                    isCompositeFound = true;
                    break;
                }
            }
            connection.outputStream.writeBoolean(isCompositeFound);
        }

        connection.close();
    }
}