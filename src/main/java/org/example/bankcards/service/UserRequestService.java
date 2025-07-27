package org.example.bankcards.service;

import org.example.bankcards.dto.UserRequestDto;

public interface UserRequestService {
    UserRequestDto userRequest(UserRequestDto userRequestDto, Long userId);
}
