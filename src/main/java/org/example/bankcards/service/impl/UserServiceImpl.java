package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.UserDto;
import org.example.bankcards.exception.custom_exceptions.RoleNotFoundException;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.UserMapper;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.service.UserService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final static Set<String> roles = Set.of("ROLE_USER", "ROLE_ADMIN");

    private final UserMapper userMapper;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    @Override
    public UserDto createUser(UserDto userDto) {
        checkCorrectRole(userDto);
        return Optional.of(userDto)
                .map(this::setUserEncodePassword)
                .map(userMapper::toEntity)
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow();
    }

    @Transactional
    @Override
    public void deleteUser(Long id) {
        userRepository.findById(id)
                .ifPresentOrElse(userRepository::delete, UserNotFoundException::getUserNotFoundException);
    }

    @Transactional
    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        checkCorrectRole(userDto);
        UserDto updatedUser = setUserEncodePassword(userDto);
        return userRepository.findById(id)
                .map(entity -> userMapper.mergeToEntity(updatedUser, entity))
                .map(userRepository::save)
                .map(userMapper::toDto)
                .orElseThrow(UserNotFoundException::getUserNotFoundException);
    }

    private void checkCorrectRole(UserDto userDto) {
        if (!roles.contains(userDto.getRole())) {
            throw RoleNotFoundException.getRoleNotFoundException();
        }
    }

    @Transactional(readOnly = true)
    @Override
    public UserDto getUser(Long id) {
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElseThrow(UserNotFoundException::getUserNotFoundException);
    }

    @Transactional(readOnly = true)
    @Override
    public List<UserDto> getAllUsers() {
        return userRepository.findAll().stream()
                .map(userMapper::toDto)
                .toList();
    }

    private UserDto setUserEncodePassword(UserDto user) {
        System.out.println(user.getPassword());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        System.out.println(user.getPassword());
        return user;
    }
}

