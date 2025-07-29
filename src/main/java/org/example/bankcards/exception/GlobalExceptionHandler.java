package org.example.bankcards.exception;

import lombok.extern.slf4j.Slf4j;
import org.example.bankcards.dto.GlobalExceptionResponseDto;
import org.example.bankcards.enums.CardStatusEnum;
import org.example.bankcards.exception.custom_exceptions.*;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestControllerAdvice(basePackages = "org.example.bankcards.controller")
public class GlobalExceptionHandler {

    @ExceptionHandler(NegativeBalanceException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleNegativeBalance(NegativeBalanceException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(CardIsNotActiveException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleCardIsNotActive(CardIsNotActiveException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.CONFLICT);
    }

    @ExceptionHandler(CardNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleCardNotFound(CardNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UserNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleUserNotFound(UserNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleRoleNotFound(RoleNotFoundException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleValidationExceptions(
            MethodArgumentNotValidException ex) {
        log.error(ex.getMessage(), ex);

        List<String> errors = new ArrayList<>();

        ex.getBindingResult().getFieldErrors().forEach(error ->
                errors.add(error.getDefaultMessage())
        );

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(errors.toString())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleDataIntegrityViolation(
            DataIntegrityViolationException ex) {
        log.error(ex.getMessage(), ex);
        String message = extractMeaningfulMessage(ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(message)
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.CONFLICT
        );
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleJsonParseException(HttpMessageNotReadableException ex) {
        log.error(ex.getMessage(), ex);
        String message = resolveDeserializationIssue(ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(message)
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST
        );
    }

    private String resolveDeserializationIssue(HttpMessageNotReadableException ex) {
        String rawMessage = ex.getMessage();

        if (rawMessage.contains("YearMonth")) {
            return "Поле expiryDate: неверный формат даты. Ожидается: ГГГГ-ММ (например, 2025-12)";
        }

        if (rawMessage.contains("CardStatusEnum")) {
            String allowed = Arrays.stream(CardStatusEnum.values())
                    .map(Enum::name)
                    .collect(Collectors.joining(", "));
            return "Поле status: недопустимое значение. Разрешены только: " + allowed;
        }
        return "Ошибка чтения JSON: " + rawMessage;
    }

    private String extractMeaningfulMessage(DataIntegrityViolationException ex) {
        String rawMessage = ex.getMessage();

        if (rawMessage.contains("card_card_number_key")) {
            return "Карта с таким номером уже существует";
        } else if (rawMessage.contains("users_name_key")) {
            return "Пользователь с таким name уже существует";
        } else if (rawMessage.contains("users_email_key")) {
            return "Пользователь с таким email уже существует";
        } else {
            return "Ошибка целостности данных";
        }
    }
}

