package org.example.bankcards.mapper;

import org.example.bankcards.dto.UserDto;
import org.example.bankcards.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

/**
 * Mapper для преобразования объектов класса {@link UserEntity} в {@link UserDto} и наоборот.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует сущность {@link UserEntity} в DTO-объект {@link UserDto}.
     *
     * @param userEntity сущность пользователя {@link UserEntity}
     * @return DTO-объект, представляющий пользователя {@link UserDto}
     */
    UserDto toDto(UserEntity userEntity);

    /**
     * Преобразует DTO-объект {@link UserDto} в сущность {@link UserEntity}.
     * <p>
     * Поле {@code id} игнорируется при преобразовании.
     *
     * @param userDto DTO-объект пользователя {@link UserDto}
     * @return сущность пользователя {@link UserEntity}
     */
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserDto userDto);

    /**
     * Обновляет существующую сущность {@link UserEntity} данными из DTO-объекта {@link UserDto}.
     * <p>
     * Поле {@code id} игнорируется, чтобы сохранить оригинальный идентификатор.
     *
     * @param userDto       DTO-объект с обновлёнными данными {@link UserDto}
     * @param userEntity    сущность, которую необходимо обновить {@link UserEntity}
     * @return обновлённая сущность {@link UserEntity}
     */
    @Mapping(target = "id", ignore = true)
    UserEntity mergeToEntity(UserDto userDto, @MappingTarget UserEntity userEntity);
}
