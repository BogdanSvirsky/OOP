package ru.nsu.svirsky;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import ru.nsu.svirsky.entities.CourseUnit;
import ru.nsu.svirsky.entities.enums.ControlType;
import ru.nsu.svirsky.entities.enums.Mark;
import ru.nsu.svirsky.entities.enums.Semester;

/**
 * Tests for recrod book.
 */
public class RecordBookTest {
    @Test
    void arithmeticMeanTest() throws Exception {
        RecordBook recordBook = new RecordBook();

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.DECLAR_PROG, ControlType.DIFF_PASS,
                        Mark.EXCELLENT));
        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.IMPERAT_PROG, ControlType.DIFF_PASS,
                        Mark.EXCELLENT));
        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.HISTORY, ControlType.EXAM, Mark.GOOD));
        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.ORG, ControlType.DIFF_PASS, Mark.GOOD));
        assertEquals(recordBook.getArithmeticMean(), (double) 4.5);
    }

    @Test
    void transferToBugdetTest() throws Exception {
        RecordBook recordBook = new RecordBook();

        recordBook.currentSemester = Semester.THIRD;

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.DIGITAL_PLAT, ControlType.DIFF_PASS,
                        Mark.GOOD));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.SECOND, ProgrammingCourse.DIGITAL_PLAT, ControlType.DIFF_PASS,
                        Mark.GOOD));
        assertTrue(recordBook.canTransferToBudget());

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.SECOND, ProgrammingCourse.DISCRETE_MATH, ControlType.EXAM, 
                        Mark.SATIS));

        assertFalse(recordBook.canTransferToBudget());
    }

    @Test
    void getRedDiplomaTest() throws Exception {
        RecordBook recordBook = new RecordBook();

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.FIRST, ProgrammingCourse.HISTORY, ControlType.EXAM, 
                        Mark.EXCELLENT));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.SECOND, ProgrammingCourse.ENGLISH, ControlType.DIFF_PASS, 
                        Mark.EXCELLENT));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.SPORT, ControlType.DIFF_PASS, 
                        Mark.EXCELLENT));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.MATH_ANALYSIS, ControlType.DIFF_PASS, 
                        Mark.GOOD));

        assertTrue(recordBook.canGetRedDiploma());

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.DISCRETE_MATH, ControlType.EXAM, 
                        Mark.GOOD));

        assertFalse(recordBook.canGetRedDiploma());
    }

    @Test
    void getIncreasedScholarShipTest() throws Exception {
        RecordBook recordBook = new RecordBook();

        recordBook.currentSemester = Semester.THIRD;

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.HISTORY, ControlType.EXAM, 
                        Mark.EXCELLENT));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.ENGLISH, ControlType.DIFF_PASS, 
                        Mark.EXCELLENT));

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.SPORT, ControlType.DIFF_PASS, 
                        Mark.EXCELLENT));

        assertTrue(recordBook.canGetIncreasedScholarShip());

        recordBook.addCourseUnit(
                new CourseUnit(
                        Semester.THIRD, ProgrammingCourse.DISCRETE_MATH, ControlType.EXAM, 
                        Mark.GOOD));

        assertFalse(recordBook.canGetIncreasedScholarShip());

    }
}
