package ru.nsu.svirsky.entities;

/**
 * Course in record book row.
 */
public class Course {
    private String name;

    public Course(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
