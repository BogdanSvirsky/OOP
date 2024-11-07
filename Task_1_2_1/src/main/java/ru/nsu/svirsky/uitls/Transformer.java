package ru.nsu.svirsky.uitls;

/**
 * Interface to convert string to generic's class object.
 *
 * @author Bogdan Svirsky
 */
public interface Transformer<T> {
    T transform(String input);
}
