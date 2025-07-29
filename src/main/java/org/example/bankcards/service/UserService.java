package org.example.bankcards.service;

import org.example.bankcards.dto.UserDto;

import java.util.List;

public interface UserService {

    UserDto createUser(UserDto userDto);

    void deleteUser(Long userId);

    UserDto updateUser(UserDto userDto, Long id);

    UserDto getUser(Long userId);

    List<UserDto> getAllUsers();
}

