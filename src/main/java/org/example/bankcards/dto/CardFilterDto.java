package org.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;

/**
 * DTO для поиска карт по параметрам банковской карты.
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Поисковый фильтр по карте")
public class CardFilterDto {

    @Schema(description = "Номер карты", example = "1234567812345678")
    String cardNumber;

    @Schema(description = "Статус карты", example = "ACTIVE")
    String status;

    @Schema(description = "Баланс карты", example = "10000")
    BigInteger balance;

    @Schema(description = "Срок действия карты (ГГГГ-ММ)", example = "2025-12")
    String expiryDate;
}
