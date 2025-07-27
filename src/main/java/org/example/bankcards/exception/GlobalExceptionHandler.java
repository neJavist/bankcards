package org.example.bankcards.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.bankcards.dto.GlobalResponseDto;
import org.example.bankcards.exception.custom_exceptions.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

/**
 * Глобальный обработчик исключений.
 * Класс предназначен для централизованной обработки исключений, возникающих в контроллерах приложения.
 * Используется аннотация {@link ControllerAdvice}, чтобы применить обработку ко всем контроллерам.
 */
@Slf4j
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<GlobalResponseDto> handleNegativeBalance(NegativeBalanceException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                GlobalResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CardIsNotActiveException.class)
    public ResponseEntity<GlobalResponseDto> handleCardIsNotActive(CardIsNotActiveException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                GlobalResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<GlobalResponseDto> handleCardNotFound(CardNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                GlobalResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalResponseDto> handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                GlobalResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<GlobalResponseDto> handleRoleNotFound(RoleNotFoundException ex) {
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(
                GlobalResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }


}

