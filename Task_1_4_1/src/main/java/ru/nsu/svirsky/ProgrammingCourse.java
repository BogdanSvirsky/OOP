package ru.nsu.svirsky;

import ru.nsu.svirsky.entities.Course;

/**
 * Courses of DIT.
 */
public class ProgrammingCourse extends Course {
    public static final ProgrammingCourse DIGITAL_PLAT =
            new ProgrammingCourse("Цифровые платформы");
    public static final ProgrammingCourse MATH_ANALYSIS = 
            new ProgrammingCourse("Введение в мат. анализ и лин. алг.");
    public static final ProgrammingCourse DISCRETE_MATH = 
            new ProgrammingCourse("Введение в дискрет. мат. и мат. лог.");
    public static final ProgrammingCourse HISTORY = new ProgrammingCourse("Истоория России");
    public static final ProgrammingCourse SPORT = 
            new ProgrammingCourse("Физ. культура и спорт");
    public static final ProgrammingCourse DECLAR_PROG = 
            new ProgrammingCourse("Декларативное программирование");
    public static final ProgrammingCourse ENGLISH = new ProgrammingCourse("Ин. яз");
    public static final ProgrammingCourse IMPERAT_PROG = 
            new ProgrammingCourse("Императивное программирование");
    public static final ProgrammingCourse ORG = 
            new ProgrammingCourse("Основы российской государственности");

    public ProgrammingCourse(String name) {
        super(name);
    }
}
