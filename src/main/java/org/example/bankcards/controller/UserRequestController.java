package org.example.bankcards.controller;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.service.UserRequestService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class UserRequestController {

    private final UserRequestService userRequestService;

    @PostMapping("/{userId}")
    public ResponseEntity<UserRequestDto> createUserRequest(@RequestBody UserRequestDto userRequestDto,
                                                            @PathVariable Long userId) {
        final UserRequestDto userRequest = userRequestService.userRequest(userRequestDto, userId);
        return ResponseEntity.ok(userRequest);
    }
}
