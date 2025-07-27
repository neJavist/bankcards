package org.example.bankcards.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.entity.CardEntity;
import org.example.bankcards.enums.CardStatus;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import java.math.BigInteger;
import java.time.YearMonth;

/**
 * Dto для {@link CardEntity}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardDto {
    Long id;

    @Size(min = 16, max = 16, message = "Card number must be exactly 16 digits")
    @Pattern(regexp = "^[0-9]{16}$", message = "Card number must contain only digits")
    String cardNumber;

    CardStatus status;

    BigInteger balance;

    YearMonth expiryDate;
}
