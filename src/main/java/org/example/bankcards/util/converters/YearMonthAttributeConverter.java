package org.example.bankcards.util.converters;

import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;
import java.time.YearMonth;

/**
 * Конвертер для работы с типом {@link YearMonth} в контексте JPA.
 * Реализует интерфейс {@link AttributeConverter}, позволяя автоматически преобразовывать объекты типа {@link YearMonth}
 * в строковое представление при сохранении в БД и обратно — при загрузке из БД.
 */
@Converter(autoApply = true)
public class YearMonthAttributeConverter implements AttributeConverter<YearMonth, String> {

    /**
     * Преобразует объект типа {@link YearMonth} в строку, подходящую для хранения в базе данных.
     *
     * @param attribute {@link YearMonth}
     * @return строковое представление даты (например, "2024-03") {@link String}
     */
    @Override
    public String convertToDatabaseColumn(YearMonth attribute) {
        return attribute != null ? attribute.toString() : null;
    }

    /**
     * Преобразует строку, полученную из базы данных, в объект типа {@link YearMonth}.
     *
     * @param dbData строка в формате "YYYY-MM" {@link String}
     * @return объект YearMonth или null, если dbData равен null {@link YearMonth}
     */
    @Override
    public YearMonth convertToEntityAttribute(String dbData) {
        return dbData != null ? YearMonth.parse(dbData) : null;
    }
}
