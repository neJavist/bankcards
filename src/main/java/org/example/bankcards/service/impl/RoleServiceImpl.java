package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.RoleDto;
import org.example.bankcards.exception.custom_exceptions.RoleNotFoundException;
import org.example.bankcards.mapper.RoleMapper;
import org.example.bankcards.repository.RoleRepository;
import org.example.bankcards.service.RoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с ролями.
 * Класс предоставляет методы для создания, удаления, обновления и получения информации о ролях,
 * @see RoleMapper - маппер для преобразования между DTO и сущностями.
 *
 * @see RoleService
 */
@Service
@RequiredArgsConstructor
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    /**
     * Создаёт новую роль на основе переданных данных.
     *
     * @param roleDto данные роли в формате DTO {@link RoleDto}
     * @return сохранённая роль в виде DTO {@link RoleDto}
     * @throws RuntimeException если произошла ошибка при обработке {@link RuntimeException}
     */
    @Transactional
    @Override
    public RoleDto createRole(RoleDto roleDto) {
        return Optional.of(roleDto)
                .map(roleMapper::toEntity)
                .map(roleRepository::save)
                .map(roleMapper::toDto)
                .orElseThrow();
    }

    /**
     * Удаляет роль по её идентификатору.
     *
     * @param id идентификатор удаляемой роли {@link Long}
     * @throws RoleNotFoundException если роль не найдена {@link RoleNotFoundException}
     */
    @Transactional
    @Override
    public void deleteRole(Long id) {
        roleRepository.findById(id)
                .ifPresentOrElse(roleRepository::delete, this::getRoleNotFoundException);
    }

    /**
     * Обновляет информацию существующей роли.
     *
     * @param roleDto обновлённые данные роли в формате DTO {@link RoleDto}
     * @return обновлённая роль в виде DTO {@link RoleDto}
     * @throws RoleNotFoundException если роль не найдена {@link RoleNotFoundException}
     */
    @Transactional
    @Override
    public RoleDto updateRole(RoleDto roleDto) {
        return roleRepository.findById(roleDto.getId())
                .map(entity -> roleMapper.mergeToEntity(roleDto, entity))
                .map(roleMapper::toDto)
                .orElseThrow(this::getRoleNotFoundException);
    }

    /**
     * Получает информацию о роли по её идентификатору.
     *
     * @param id идентификатор роли {@link Long}
     * @return информация о роли в виде DTO {@link RoleDto}
     * @throws RoleNotFoundException если роль не найдена {@link RoleNotFoundException}
     */
    @Transactional(readOnly = true)
    @Override
    public RoleDto getRole(Long id) {
        return roleRepository
                .findById(id)
                .map(roleMapper::toDto)
                .orElseThrow(this::getRoleNotFoundException);
    }

    /**
     * Получает список всех ролей.
     *
     * @return список ролей в виде DTO {@link RoleDto}
     */
    @Transactional(readOnly = true)
    @Override
    public List<RoleDto> getAllRoles() {
        return roleRepository.findAll().stream()
                .map(roleMapper::toDto)
                .toList();
    }

    private RoleNotFoundException getRoleNotFoundException() {
        return new RoleNotFoundException("Role not found");
    }
}



