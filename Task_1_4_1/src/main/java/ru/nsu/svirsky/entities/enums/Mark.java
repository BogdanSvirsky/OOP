package ru.nsu.svirsky.entities.enums;

/**
 * Mark in record book row.
 */
public enum Mark {
    UNSATIS("Неудовлетворительно", 2), SATIS("Удовлетворительно", 3),
    GOOD("Хорошо", 4), EXCELLENT("Отлично", 5), PASS("Зачет", null),
    FAIL("Незачёт", null);

    private String name;
    private Integer value;

    Mark(String name, Integer value) {
        this.name = name;
        this.value = value;
    }

    public Integer getValue() {
        return value;
    }

    @Override
    public String toString() {
        return name;
    }
}
