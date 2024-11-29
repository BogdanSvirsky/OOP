package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.entities.enums.ControlType;
import ru.nsu.svirsky.entities.enums.Mark;
import ru.nsu.svirsky.entities.enums.Semester;

/**
 * Record book row.
 */
public record CourseUnit(Semester semester, Course course, ControlType controlType, Mark mark) {
}
