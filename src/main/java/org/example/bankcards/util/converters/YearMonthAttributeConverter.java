package org.example.bankcards.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;

/**
 * Конвертер, реализующий интерфейс {@link AttributeConverter}, для преобразования между
 * объектом {@link YearMonth} и его строковым представлением в базе данных.
 */
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    /**
     * Преобразует значение {@link YearMonth} в строку для хранения в базе данных.
     *
     * @param attribute объект типа {@link YearMonth}, который нужно преобразовать
     * @return строковое представление даты в формате "ГГГГ-ММ", или null, если атрибут равен null
     */
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    /**
     * Преобразует строковое значение из базы данных в объект типа {@link YearMonth}.
     *
     * @param dbData строковое значение даты в формате "ГГГГ-ММ", которое нужно преобразовать
     * @return объект типа {@link YearMonth}, или null, если входная строка равна null
     */
    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return dbData != null ? YearMonth.parse(dbData) : null;
    }
}
