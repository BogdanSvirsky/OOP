package ru.nsu.svirsky.uitls;

public class VertexNotFoundException extends Exception {
    public VertexNotFoundException(String msg) {
        super("Vertex not in Graph!" + msg);
    }
}
