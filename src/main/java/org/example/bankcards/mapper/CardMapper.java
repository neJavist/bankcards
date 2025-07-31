package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper для преобразования объектов класса {@link CardEntity} в {@link CardDto} и наоборот.
 */
@Mapper(componentModel = "spring")
public interface CardMapper {

    /**
     * Преобразует сущность {@link CardEntity} в DTO-объект {@link CardDto}.
     *
     * @param card сущность карты {@link CardEntity}
     * @return DTO-объект, представляющий карту {@link CardDto}
     */
    CardDto toDto(CardEntity card);

    /**
     * Преобразует DTO-объект {@link CardDto} в сущность {@link CardEntity}.
     * <p>
     * Поле {@code id} игнорируется при преобразовании.
     *
     * @param cardDto DTO-объект карты {@link CardDto}
     * @return сущность карты {@link CardEntity}
     */
    @Mapping(target = "id", ignore = true)
    CardEntity toEntity(CardDto cardDto);

    /**
     * Обновляет существующую сущность {@link CardEntity} данными из DTO-объекта {@link CardDto}.
     * <p>
     * Поле {@code id} игнорируется, чтобы сохранить оригинальный идентификатор.
     *
     * @param cardDto        DTO-объект с обновлёнными данными {@link CardDto}
     * @param cardEntity     сущность, которую необходимо обновить {@link CardEntity}
     * @return обновлённая сущность {@link CardEntity}
     */
    @Mapping(target = "id", ignore = true)
    CardEntity mergeToEntity(CardDto cardDto, @MappingTarget CardEntity cardEntity);
}
