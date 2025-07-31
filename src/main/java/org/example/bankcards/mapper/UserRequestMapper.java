package org.example.bankcards.mapper;

import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.entity.UserRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper для преобразования объектов класса {@link UserRequestEntity} в {@link UserRequestDto} и наоборот.
 */
@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    /**
     * Преобразует сущность {@link UserRequestEntity} в DTO-объект {@link UserRequestDto}.
     *
     * @param userRequestEntity сущность запроса пользователя {@link UserRequestEntity}
     * @return DTO-объект, представляющий запрос пользователя {@link UserRequestDto}
     */
    UserRequestDto toDto(UserRequestEntity userRequestEntity);

    /**
     * Преобразует DTO-объект {@link UserRequestDto} в сущность {@link UserRequestEntity}.
     * <p>
     * Поле {@code id} игнорируется при преобразовании.
     *
     * @param userRequestDto DTO-объект запроса пользователя {@link UserRequestDto}
     * @return сущность запроса пользователя {@link UserRequestEntity}
     */
    @Mapping(target = "id", ignore = true)
    UserRequestEntity toEntity(UserRequestDto userRequestDto);
}
