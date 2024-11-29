package ru.nsu.svirsky.entities.enums;

public enum ControlType {
    TASK("Задание"), CONTROL_WORK("Контрольная работа"), COLLOQUIM("Коллоквиум"),
    EXAM("Экзамен"), DIFF_PASS("Дифференцированный зачет"), PASS("Зачет"),
    PRACTICE("Защита отчета по практике"), DIPLOMA("Защита ВКР");

    private String name;

    ControlType(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
