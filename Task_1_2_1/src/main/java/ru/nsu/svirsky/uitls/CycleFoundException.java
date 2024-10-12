package ru.nsu.svirsky.uitls;

public class CycleFoundException extends Exception {
    public CycleFoundException(String s) {
        super("Graph has to be acyclic!" + s);
    }
}
