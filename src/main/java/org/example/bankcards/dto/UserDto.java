package org.example.bankcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
 * DTO для представления глобальной ошибки.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Пользователь")
public class UserDto {

    @JsonIgnore
    @Schema(hidden = true)
    Long id;

    @NotBlank(message = "Имя не должно быть пустым")
    @Size(min = 5, max = 50, message = "Имя должно содержать от 5 до 50 символов")
    @Schema(description = "Имя пользователя", example = "testuser")
    String name;

    @NotBlank(message = "Email не должен быть пустым")
    @Email(message = "Неверный формат email")
    @Schema(description = "Адрес электронной почты", example = "testuser@example.com")
    String email;

    @NotBlank(message = "Пароль не должен быть пустым")
    @Size(min = 5, max = 50, message = "Пароль должен быть от 5 до 50 символов")
    @Schema(description = "Пароль пользователя", example = "password")
    String password;

    @NotBlank(message = "Роль обязательна для заполнения")
    @Schema(description = "Роль пользователя", example = "ROLE_USER")
    String role;
}


