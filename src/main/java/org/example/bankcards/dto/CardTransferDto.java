package org.example.bankcards.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;

/**
 * DTO для передачи данных для перевода средств между картами.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Перевод денежных средств между картами")
public class CardTransferDto {

    @JsonIgnore
    @Schema(hidden = true)
    Long id;

    @NotBlank(message = "Номер карты получателя обязателен для заполнения")
    @Size(min = 16, max = 16, message = "Номер карты получателя должен содержать ровно 16 цифр")
    @Pattern(regexp = "^[0-9]{16}$", message = "Номер карты получателя должен содержать только цифры")
    @Schema(description = "Номер карты получателя", example = "1234567812345678")
    String cardNumberTo;

    @NotBlank(message = "Номер карты отправителя обязателен для заполнения")
    @Size(min = 16, max = 16, message = "Номер карты отправителя должен содержать ровно 16 цифр")
    @Pattern(regexp = "^[0-9]{16}$", message = "Номер карты отправителя должен содержать только цифры")
    @Schema(description = "Номер карты отправителя", example = "8765432187654321")
    String cardNumberFrom;

    @NotNull(message = "Сумма перевода обязательна для заполнения")
    @Positive(message = "Сумма перевода должна быть положительной")
    @Schema(description = "Сумма перевода", example = "15000")
    BigInteger amount;

    @JsonIgnore
    @Schema(hidden = true)
    LocalDateTime transferTime;
}
