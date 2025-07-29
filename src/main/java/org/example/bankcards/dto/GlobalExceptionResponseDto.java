package org.example.bankcards.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(callSuper = false)
@FieldDefaults(level = AccessLevel.PRIVATE)
@Schema(description = "Ответ при глобальной ошибке")
public class GlobalExceptionResponseDto {

    @Schema(description = "Сообщение об ошибке", example = "Некорректный запрос")
    String errorMessage;

    @Schema(description = "Время возникновения ошибки", example = "2025-07-29T12:34:56")
    LocalDateTime errorTime;
}
