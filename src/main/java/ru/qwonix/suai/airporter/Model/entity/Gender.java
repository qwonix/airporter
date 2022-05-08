package ru.qwonix.suai.airporter.model.entity;

public enum Gender {
    MALE('M'),
    FEMALE('F');

    private final char code;

    Gender(char code) {
        this.code = code;
    }

    public char getCode() {
        return code;
    }

    public static Gender fromCode(char code) {
        for (Gender gender : Gender.values()) {
            if (gender.code == Character.toUpperCase(code)) {
                return gender;
            }
        }

        throw new IllegalArgumentException("no such gender with code: " + code);
    }
}
