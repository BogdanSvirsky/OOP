package ru.nsu.svirsky;

import ru.nsu.svirsky.entities.Course;

public class DITCourse extends Course {
    public static final DITCourse DIGITAL_PLAT = new DITCourse("Цифровые платформы");
    public static final DITCourse MATH_ANALYSIS = new DITCourse("Введение в мат. анализ и лин. алг.");
    public static final DITCourse DISCRETE_MATH = new DITCourse("Введение в дискрет. мат. и мат. лог.");
    public static final DITCourse HISTORY = new DITCourse("Истоория России");
    public static final DITCourse SPORT = new DITCourse("Физ. культура и спорт");
    public static final DITCourse DECLAR_PROG = new DITCourse("Декларативное программирование");
    public static final DITCourse ENGLISH = new DITCourse("Ин. яз");
    public static final DITCourse IMPERAT_PROG = new DITCourse("Императивное программирование");
    public static final DITCourse ORG = new DITCourse("Основы российской государственности");


    public DITCourse(String name) {
        super(name);
    }
}
