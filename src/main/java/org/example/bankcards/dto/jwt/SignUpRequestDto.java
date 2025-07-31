package org.example.bankcards.dto.jwt;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

/**
 * DTO для передачи данных, необходимых для регистрации нового пользователя.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Запрос на регистрацию нового пользователя")
public class SignUpRequestDto {

    @Size(min = 5, max = 50, message = "Имя пользователя должно содержать от 5 до 50 символов")
    @NotBlank(message = "Имя пользователя не может быть пустым")
    @Schema(description = "Имя пользователя", example = "admin")
    String username;

    @Size(min = 5, max = 50, message = "Адрес электронной почты должен содержать от 5 до 50 символов")
    @NotBlank(message = "Адрес электронной почты не может быть пустым")
    @Email(message = "Email адрес должен быть в формате user@example.com")
    @Schema(description = "Адрес электронной почты", example = "admin@example.com")
    String email;

    @NotBlank(message = "Пароль не может быть пустым")
    @Size(min = 5, max = 50, message = "Длина пароля должна быть от 5 до 50 символов")
    @Schema(description = "Пароль", example = "password")
    String password;
}
