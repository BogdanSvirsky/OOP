package ru.nsu.svirsky.uitls.exceptions;

public class CycleFoundException extends GraphException {
    public CycleFoundException(String s) {
        super("Graph has to be acyclic!" + s);
    }
}
