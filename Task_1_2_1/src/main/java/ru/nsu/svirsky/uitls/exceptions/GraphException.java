package ru.nsu.svirsky.uitls.exceptions;

/**
 * Graph exceptions.
 *
 * @author Bogdan Svirsky
 */
public class GraphException extends Exception {
    public GraphException() {
        super();
    }
    
    public GraphException(String msg) {
        super(msg);
    }
}
