package org.example.bankcards.mapper;

import org.example.bankcards.dto.RoleDto;
import org.example.bankcards.entity.RoleEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

/**
 * Mapper для преобразования между сущностью {@link RoleEntity} и DTO-объектом {@link RoleDto}.
 * Реализует методы для перевода данных между слоями приложения, используя библиотеку MapStruct.
 */
@Mapper(componentModel = "spring")
public interface RoleMapper {

    /**
     * Преобразует сущность {@link RoleEntity} в объект DTO {@link RoleDto}.
     *
     * @param roleEntity сущность роли {@link RoleEntity}
     * @return объект DTO роли {@link RoleDto}
     */
    RoleDto toDto(RoleEntity roleEntity);

    /**
     * Преобразует объект DTO {@link RoleDto} в сущность {@link RoleEntity}.
     * Поле `id` игнорируется, чтобы избежать перезаписи первичного ключа при обновлении.
     *
     * @param roleDto данные DTO роли {@link RoleDto}
     * @return сущность роли {@link RoleEntity}
     */
    @Mapping(target = "id", ignore = true)
    RoleEntity toEntity(RoleDto roleDto);

    /**
     * Преобразует список сущностей {@link RoleEntity} в список объектов DTO {@link RoleDto}.
     *
     * @param roles список сущностей ролей {@link RoleEntity}
     * @return список DTO ролей {@link RoleDto}
     */
    List<RoleDto> toListDto(List<RoleEntity> roles);

    /**
     * Обновляет существующую сущность {@link RoleEntity} данными из объекта DTO {@link RoleDto}.
     * Поле `id` не изменяется, чтобы сохранить идентификатор при обновлении.
     *
     * @param roleDto        данные для обновления {@link RoleDto}
     * @param roleEntity     сущность, которую нужно обновить {@link RoleEntity}
     * @return обновлённая сущность {@link RoleEntity}
     */
    @Mapping(target = "id", ignore = true)
    RoleEntity mergeToEntity(RoleDto roleDto, @MappingTarget RoleEntity roleEntity);
}
