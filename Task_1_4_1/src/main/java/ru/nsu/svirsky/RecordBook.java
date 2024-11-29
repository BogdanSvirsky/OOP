package ru.nsu.svirsky;

import java.util.HashSet;
import java.util.Set;
import ru.nsu.svirsky.entities.Course;
import ru.nsu.svirsky.entities.CourseUnit;
import ru.nsu.svirsky.entities.enums.ControlType;
import ru.nsu.svirsky.entities.enums.Mark;
import ru.nsu.svirsky.entities.enums.Semester;

/**
 * Record book implementation.
 */
public class RecordBook {
    public static final double RED_DIPLOMA_PERCENT = 0.75;
    public Semester currentSemester = Semester.FIRST;
    private Set<CourseUnit> courseUnits = new HashSet<CourseUnit>();
    private Set<Course> courses = new HashSet<>();

    public double getArithmeticMean() {
        return courseUnits.stream()
                .map(courseUnit -> courseUnit.mark())
                .filter(mark -> mark.getValue() != null)
                .mapToInt(mark -> mark.getValue().intValue())
                .average().getAsDouble();
    }

    public void nextSemester() {
        currentSemester = currentSemester.next();
    }

    public Semester getCurrentSemester() {
        return currentSemester;
    }

    public boolean canTransferToBudget() {
        if (currentSemester == Semester.FIRST || currentSemester == Semester.SECOND) {
            return false;
        }

        return courseUnits.stream()
                .filter(x -> (x.semester() == currentSemester.prev()
                        || x.semester() == currentSemester.prev().prev()))
                .filter(x -> x.controlType() == ControlType.DIFF_PASS
                        || x.controlType() == ControlType.EXAM)
                .noneMatch(x -> x.mark() == Mark.FAIL || x.mark() == Mark.UNSATIS
                        || x.mark() == Mark.SATIS);
    }

    public void addCourseUnit(CourseUnit courseUnit) throws Exception {
        if (courseUnits.contains(courseUnit)) {
            throw new Exception("course unit already exists");
        }

        courseUnits.add(courseUnit);
        courses.add(courseUnit.course());
    }

    private Mark getCourseMark(Course course) {
        return courseUnits.stream()
                .filter(x -> x.course() == course)
                .map(x -> x.mark())
                .toList().getLast();
    }

    public boolean canGetRedDiploma() {
        boolean result = 
                getArithmeticMean() >= 4 + RED_DIPLOMA_PERCENT;
        result = result
                && courses.stream()
                        .map(course -> getCourseMark(course))
                        .noneMatch(mark -> mark == Mark.UNSATIS || mark == Mark.FAIL
                                || mark == Mark.SATIS);
        result = result && (courseUnits.stream()
                .anyMatch(courseUnit -> courseUnit.controlType() == ControlType.DIPLOMA
                        && courseUnit.mark() == Mark.EXCELLENT)
                || courseUnits.stream()
                        .noneMatch(
                                courseUnit -> courseUnit.controlType() == ControlType.DIPLOMA));
        return result;
    }

    public boolean canGetIncreasedScholarShip() {
        return courseUnits.stream()
                .filter(courseUnit -> courseUnit.semester() == currentSemester)
                .allMatch(courseUnit -> courseUnit.mark() == Mark.EXCELLENT
                        || courseUnit.mark() == Mark.PASS);
    }
}
