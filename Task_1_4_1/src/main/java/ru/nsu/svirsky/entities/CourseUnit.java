package ru.nsu.svirsky.entities;

import ru.nsu.svirsky.entities.enums.ControlType;
import ru.nsu.svirsky.entities.enums.Mark;
import ru.nsu.svirsky.entities.enums.Semester;

public record CourseUnit(Semester semester, Course course, ControlType controlType, Mark mark) {
}
