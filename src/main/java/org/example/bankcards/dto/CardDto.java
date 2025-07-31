package org.example.bankcards.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
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

import java.math.BigInteger;
import java.time.YearMonth;

/**
 * DTO для представления информации о банковской карте.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Банковская карта")
public class CardDto {

    @Schema(hidden = true)
    @JsonIgnore
    Long id;

    @NotBlank(message = "Номер карты обязателен для заполнения")
    @Size(min = 16, max = 16, message = "Номер карты должен состоять ровно из 16 цифр")
    @Pattern(regexp = "^[0-9]{16}$", message = "Номер карты должен содержать только цифры")
    @Schema(description = "Номер карты", example = "1234567812345678")
    String cardNumber;

    @NotNull(message = "Статус карты обязателен для заполнения")
    @Schema(description = "Статус карты", example = "ACTIVE")
    CardStatusEnum status;

    @NotNull(message = "Баланс карты обязателен для заполнения")
    @PositiveOrZero(message = "Баланс карты должен быть положительным или равным нулю")
    @Schema(description = "Баланс карты", example = "10000")
    BigInteger balance;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM")
    @NotNull(message = "Срок действия карты обязателен для заполнения")
    @Schema(description = "Срок действия карты (ГГГГ-ММ)", example = "2025-12")
    YearMonth expiryDate;
}

