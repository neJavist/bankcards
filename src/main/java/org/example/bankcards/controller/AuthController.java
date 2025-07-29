package org.example.bankcards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.jwt.JwtAuthenticationResponseDto;
import org.example.bankcards.dto.jwt.SignInRequestDto;
import org.example.bankcards.dto.jwt.SignUpRequestDto;
import org.example.bankcards.security.service.AuthenticationService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthenticationService authenticationService;

    @Operation(summary = "Регистрация пользователя", description = "Создаёт нового пользователя и возвращает токен.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь зарегистрирован",
                    content = { @Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponseDto.class)) }),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "409", description = "Пользователь с таким email уже существует"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/sign-up")
    public JwtAuthenticationResponseDto signUp(
            @Parameter(description = "Данные для регистрации", required = true)
            @RequestBody @Valid SignUpRequestDto request) {
        return authenticationService.signUp(request);
    }

    @Operation(summary = "Авторизация пользователя", description = "Авторизация пользователя и возвращает токен.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь авторизован",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = JwtAuthenticationResponseDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден")
    })
    @PostMapping("/sign-in")
    public JwtAuthenticationResponseDto signIn(
            @Parameter(description = "Данные для входа", required = true)
            @RequestBody @Valid SignInRequestDto request) {
        return authenticationService.signIn(request);
    }
}
