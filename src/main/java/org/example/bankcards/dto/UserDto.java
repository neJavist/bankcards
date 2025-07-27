package org.example.bankcards.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.entity.UserEntity;

import java.util.List;

/**
 * Dto для {@link UserEntity}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserDto {
    Long id;
    String name;
    String email;
    String password;
    List<CardTransferDto> cardTransfers;
    List<CardDto> cards;
    List<RoleDto> roles;
}
