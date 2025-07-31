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

/**
 * Глобальный обработчик исключений для всех контроллеров приложения.
 */
@Slf4j
@RestControllerAdvice(basePackages = "org.example.bankcards.controller")
public class GlobalExceptionHandler {

    /**
     * Обрабатывает исключение {@link RuntimeException}.
     * <p>
     * Возвращает HTTP-код 400 (BAD_REQUEST) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link RuntimeException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<GlobalExceptionResponseDto> handleNegativeBalance(RuntimeException ex) {
        log.error(ex.getMessage(), ex);

        return new ResponseEntity<>(
                GlobalExceptionResponseDto.builder()
                        .errorMessage(ex.getMessage())
                        .errorTime(LocalDateTime.now())
                        .build(),
                HttpStatus.BAD_REQUEST);
    }

    /**
     * Обрабатывает исключение {@link NegativeBalanceException}.
     * <p>
     * Возвращает HTTP-код 400 (BAD_REQUEST) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link NegativeBalanceException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link CardIsNotActiveException}.
     * <p>
     * Возвращает HTTP-код 409 (CONFLICT) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link CardIsNotActiveException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link CardNotFoundException}.
     * <p>
     * Возвращает HTTP-код 404 (NOT_FOUND) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link CardNotFoundException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link UserNotFoundException}.
     * <p>
     * Возвращает HTTP-код 404 (NOT_FOUND) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link UserNotFoundException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link RoleNotFoundException}.
     * <p>
     * Возвращает HTTP-код 404 (NOT_FOUND) и сообщение об ошибке.
     *
     * @param ex исключение, которое произошло {@link RoleNotFoundException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link MethodArgumentNotValidException}.
     * <p>
     * Собирает все ошибки валидации и возвращает их в виде одного сообщения.
     * Возвращает HTTP-код 400 (BAD_REQUEST).
     *
     * @param ex исключение, которое произошло {@link MethodArgumentNotValidException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link DataIntegrityViolationException}.
     * <p>
     * Извлекает понятное сообщение об ошибке целостности данных.
     * Возвращает HTTP-код 409 (CONFLICT).
     *
     * @param ex исключение, которое произошло {@link DataIntegrityViolationException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Обрабатывает исключение {@link HttpMessageNotReadableException}.
     * <p>
     * Возвращает понятное сообщение о проблеме десериализации JSON.
     * Возвращает HTTP-код 400 (BAD_REQUEST).
     *
     * @param ex исключение, которое произошло {@link HttpMessageNotReadableException}
     * @return ответ в виде {@link ResponseEntity} с объектом {@link GlobalExceptionResponseDto}
     */
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

    /**
     * Формирует понятное сообщение об ошибке десериализации JSON.
     * <p>
     * Проверяет тип ошибки и возвращает соответствующее сообщение.
     *
     * @param ex исключение, связанное с десериализацией {@link HttpMessageNotReadableException}
     * @return понятное сообщение об ошибке
     */
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

    /**
     * Извлекает понятное сообщение из исключения {@link DataIntegrityViolationException}.
     * <p>
     * Анализирует исходное сообщение и определяет причину нарушения целостности данных.
     *
     * @param ex исключение, связанное с нарушением целостности данных {@link DataIntegrityViolationException}
     * @return понятное сообщение об ошибке
     */
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
