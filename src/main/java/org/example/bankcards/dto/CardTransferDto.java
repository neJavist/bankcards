package org.example.bankcards.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CardTransferDto {
    Long id;
    String cardNumberTo;
    String cardNumberFrom;
    LocalDateTime transferTime;
    BigInteger amount;
}
