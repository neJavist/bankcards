package org.example.bankcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
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
import org.example.bankcards.enums.CardStatusEnum;

import java.time.LocalDateTime;

/**
 * DTO для запроса пользователя на блокировку карты.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Запрос пользователя на блокировку карты")
public class UserRequestDto {

    @JsonIgnore
    @Schema(hidden = true)
    Long id;

    @JsonIgnore
    @Schema(hidden = true)
    Long userId;

    @NotBlank(message = "Номер карты не должен быть пустым")
    @Size(min = 16, max = 16, message = "Номер карты должен содержать ровно 16 символов")
    @Schema(description = "Номер карты для блокировки", example = "1234567812345678")
    String cardNumber;

    @JsonIgnore
    @Schema(hidden = true)
    CardStatusEnum toStatus;

    @JsonIgnore
    @Schema(hidden = true)
    LocalDateTime requestTime;
}


