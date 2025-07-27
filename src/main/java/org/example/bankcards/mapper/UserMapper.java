package org.example.bankcards.mapper;

import org.example.bankcards.dto.UserDto;
import org.example.bankcards.entity.UserEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для преобразования между сущностью {@link UserEntity} и DTO-объектом {@link UserDto}.
 * Реализует методы для перевода данных между слоями приложения, используя библиотеку MapStruct.
 */
@Mapper(componentModel = "spring")
public interface UserMapper {

    /**
     * Преобразует сущность {@link UserEntity} в объект DTO {@link UserDto}.
     *
     * @param userEntity сущность пользователя {@link UserEntity}
     * @return объект DTO пользователя {@link UserDto}
     */
    UserDto toDto(UserEntity userEntity);

    /**
     * Преобразует объект DTO {@link UserDto} в сущность {@link UserEntity}.
     * Поле `id` игнорируется, чтобы избежать перезаписи первичного ключа при обновлении.
     *
     * @param userDto данные DTO пользователя {@link UserDto}
     * @return сущность пользователя {@link UserEntity}
     */
    @Mapping(target = "id", ignore = true)
    UserEntity toEntity(UserDto userDto);

    /**
     * Преобразует список сущностей {@link UserEntity} в список объектов DTO {@link UserDto}.
     *
     * @param users список сущностей пользователей {@link UserEntity}
     * @return список DTO пользователей {@link UserDto}
     */
    List<UserDto> toListDto(List<UserEntity> users);

    /**
     * Обновляет существующую сущность {@link UserEntity} данными из объекта DTO {@link UserDto}.
     * Поле `id` не изменяется, чтобы сохранить идентификатор при обновлении.
     *
     * @param userDto        данные для обновления {@link UserDto}
     * @param userEntity     сущность, которую нужно обновить {@link UserEntity}
     * @return обновлённая сущность {@link UserEntity}
     */
    @Mapping(target = "id", ignore = true)
    UserEntity mergeToEntity(UserDto userDto, @MappingTarget UserEntity userEntity);
}

