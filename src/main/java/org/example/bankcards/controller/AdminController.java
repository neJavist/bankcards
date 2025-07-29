package org.example.bankcards.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.UserDto;
import org.example.bankcards.service.CardBusinessService;
import org.example.bankcards.service.CardService;
import org.example.bankcards.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CardService cardService;
    private final CardBusinessService cardBusinessService;

    @Operation(summary = "Создать карту", description = "Создаёт новую карту для указанного пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта успешно создана",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/cards/{userId}")
    public ResponseEntity<CardDto> createCard(
            @Valid @RequestBody CardDto cardDto,
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long userId) {
        return ResponseEntity.ok(cardService.createCard(cardDto, userId));
    }

    @Operation(summary = "Заблокировать карту", description = "Заблокирует карту по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта заблокирована",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PatchMapping("/cards/{id}/block")
    public ResponseEntity<CardDto> blockCard(
            @Parameter(description = "ID карты", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(cardBusinessService.blockCard(id));
    }

    @Operation(summary = "Активировать карту", description = "Активирует карту по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта активирована",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CardDto.class))}),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PatchMapping("/cards/{id}/active")
    public ResponseEntity<CardDto> activeCard(
            @Parameter(description = "ID карты", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(cardBusinessService.activateCard(id));
    }

    @Operation(summary = "Удалить карту", description = "Удаляет карту по её ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Карта удалена", content = {
                    @Content(mediaType = "application/json", schema = @Schema(type = "number"))}),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/cards/{id}")
    public ResponseEntity<Long> deleteCard(
            @Parameter(description = "ID карты", example = "1") @PathVariable Long id) {
        cardService.deleteCard(id);
        return ResponseEntity.ok(id);
    }

    @Operation(summary = "Получить все карты", description = "Возвращает список всех карт.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/cards")
    public ResponseEntity<List<CardDto>> getAllCards() {
        return ResponseEntity.ok(cardService.getAllCards());
    }

    @Operation(summary = "Создать пользователя", description = "Создаёт нового пользователя.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь создан",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/users")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto) {
        return ResponseEntity.ok(userService.createUser(userDto));
    }

    @Operation(summary = "Обновить пользователя", description = "Обновляет информацию о пользователе.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь обновлён",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PatchMapping("/users/{id}")
    public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto,
                                              @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(userService.updateUser(userDto, id));
    }

    @Operation(summary = "Удалить пользователя", description = "Удаляет пользователя по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Пользователь удалён"),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Получить пользователя", description = "Возвращает пользователя по его ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Пользователь найден",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserDto.class))}),
            @ApiResponse(responseCode = "404", description = "Пользователь не найден"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")

    })
    @GetMapping("/users/{id}")
    public ResponseEntity<UserDto> getUserById(
            @Parameter(description = "ID пользователя", example = "1") @PathVariable Long id) {
        return ResponseEntity.ok(userService.getUser(id));
    }

    @Operation(summary = "Получить всех пользователей", description = "Возвращает список всех пользователей.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список пользователей",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUser() {
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
