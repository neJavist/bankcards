package org.example.bankcards.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.enums.CardStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequestDto {
    Long id;
    Long userId;
    String cardNumber;
    CardStatus toStatus;
    LocalDateTime requestTime;
}
