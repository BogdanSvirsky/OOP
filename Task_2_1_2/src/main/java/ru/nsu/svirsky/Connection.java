package ru.nsu.svirsky;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {
    private final Socket connSocket;
    public final DataInputStream inputStream;
    public final DataOutputStream outputStream;

    public Connection(Socket connSocket) throws IOException {
        this.connSocket = connSocket;
        connSocket.setSoTimeout(1);
        inputStream = new DataInputStream(connSocket.getInputStream());
        outputStream = new DataOutputStream(connSocket.getOutputStream());
    }

    public void close() throws IOException {
        inputStream.close();
        outputStream.close();
        connSocket.close();
    }
}
