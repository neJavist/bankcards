package org.example.bankcards.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.entity.RoleEntity;

/**
 * Dto для {@link RoleEntity}
 */
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RoleDto {
    Long id;
    String name;
}
