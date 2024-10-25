package ru.nsu.svirsky.uitls.exceptions;

public class VertexNotFoundException extends GraphException {
    public VertexNotFoundException() {
        super();
    }

    public VertexNotFoundException(String msg) {
        super("Vertex not in Graph!" + msg);
    }
}
