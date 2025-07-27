package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для преобразования между сущностью {@link CardEntity} и DTO-объектом {@link CardDto}.
 * Реализует методы для перевода данных между слоями приложения, используя библиотеку MapStruct.
 */
@Mapper(componentModel = "spring")
public interface CardMapper {

    /**
     * Преобразует сущность {@link CardEntity} в объект DTO {@link CardDto}.
     *
     * @param card объект типа {@link CardEntity}
     * @return объект типа {@link CardDto}
     */
    CardDto toDto(CardEntity card);

    /**
     * Преобразует объект DTO {@link CardDto} в сущность {@link CardEntity}.
     * Поле `id` игнорируется, чтобы избежать перезаписи первичного ключа при обновлении.
     *
     * @param cardDto объект типа {@link CardDto}
     * @return объект типа {@link CardEntity}
     */
    @Mapping(target = "id", ignore = true)
    CardEntity toEntity(CardDto cardDto);

    /**
     * Преобразует список сущностей {@link CardEntity} в список объектов DTO {@link CardDto}.
     *
     * @param cards список объектов типа {@link CardEntity}
     * @return список объектов типа {@link CardDto}
     */
    List<CardDto> toListDto(List<CardEntity> cards);

    /**
     * Обновляет существующую сущность {@link CardEntity} данными из объекта DTO {@link CardDto}.
     * Поле `id` не изменяется, чтобы сохранить идентификатор при обновлении.
     *
     * @param cardDto    данные для обновления {@link CardDto}
     * @param cardEntity сущность, которую нужно обновить {@link CardEntity}
     * @return обновлённая сущность {@link CardEntity}
     */
    @Mapping(target = "id", ignore = true)
    CardEntity mergeToEntity(CardDto cardDto, @MappingTarget CardEntity cardEntity);
}

