package ru.nsu.svirsky.uitls.exceptions;

/**
 * Cycle was founded in toposort exception.
 *
 * @author Bogdan Svirsky
 */
public class CycleFoundException extends GraphException {
    public CycleFoundException(String s) {
        super("Graph has to be acyclic!" + s);
    }
}
