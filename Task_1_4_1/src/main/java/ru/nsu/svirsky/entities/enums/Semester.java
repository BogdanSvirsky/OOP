package ru.nsu.svirsky.entities.enums;

public enum Semester {
    FIRST, SECOND, THIRD, FOURTH, FIVETH, SIXTH, SEVENTH, EIGHTH;

    public Semester next() {
        return switch (this) {
            case FIRST -> SECOND;
            case SECOND -> THIRD;
            case THIRD -> FOURTH;
            case FOURTH -> FIVETH;
            case FIVETH -> SIXTH;
            case SIXTH -> SEVENTH;
            case SEVENTH -> EIGHTH;
            case EIGHTH -> null;
        };
    }

    public Semester prev() {
        return switch (this) {
            case FIRST -> null;
            case SECOND -> FIRST;
            case THIRD -> SECOND;
            case FOURTH -> THIRD;
            case FIVETH -> FOURTH;
            case SIXTH -> FIVETH;
            case SEVENTH -> SIXTH;
            case EIGHTH -> SEVENTH;
        };
    }
}
