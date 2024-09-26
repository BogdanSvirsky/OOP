package ru.nsu.svirsky.utils;

/**
 * Interface for transferring all work with user output to Main.
 * 
 * @author Bogdan Svirsky
 */
public interface Notifier {
    void notify(String text);
}
