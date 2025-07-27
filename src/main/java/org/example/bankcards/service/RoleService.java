package org.example.bankcards.service;

import org.example.bankcards.dto.RoleDto;

import java.util.List;

/**
 * Сервисный интерфейс для работы с ролями.
 * Определяет методы для создания, удаления, обновления и получения информации о ролях.
 */
public interface RoleService {

    /**
     * Создаёт новую роль на основе переданных данных.
     *
     * @param roleDto данные роли в формате DTO {@link RoleDto}
     * @return сохранённая роль в виде DTO {@link RoleDto}
     */
    RoleDto createRole(RoleDto roleDto);

    /**
     * Удаляет роль по её идентификатору.
     *
     * @param roleId идентификатор удаляемой роли {@link Long}
     */
    void deleteRole(Long roleId);

    /**
     * Обновляет информацию существующей роли.
     *
     * @param roleDto обновлённые данные роли в формате DTO {@link RoleDto}
     * @return обновлённая роль в виде DTO {@link RoleDto}
     */
    RoleDto updateRole(RoleDto roleDto);

    /**
     * Получает информацию о роли по её идентификатору.
     *
     * @param roleId идентификатор роли {@link Long}
     * @return информация о роли в виде DTO {@link RoleDto}
     */
    RoleDto getRole(Long roleId);

    /**
     * Получает список всех ролей.
     *
     * @return список ролей в виде DTO {@link RoleDto}
     */
    List<RoleDto> getAllRoles();
}

