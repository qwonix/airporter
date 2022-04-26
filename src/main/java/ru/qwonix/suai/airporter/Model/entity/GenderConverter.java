package ru.qwonix.suai.airporter.model.entity;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class GenderConverter
        implements AttributeConverter<Gender, Character> {


    @Override
    public Gender convertToEntityAttribute(Character value) {
        if (value == null) {
            return null;
        }

        return Gender.fromCode(value);
    }

    @Override
    public Character convertToDatabaseColumn(Gender value) {
        if (value == null) {
            return null;
        }

        return value.getCode();
    }
}
