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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST-контроллер административного API.
 * Обрабатывает запросы, связанные с управлением пользователями и картами.
 */
@RestController
@RequestMapping("/api/admin")
@RequiredArgsConstructor
public class AdminController {

    private final UserService userService;
    private final CardService cardService;
    private final CardBusinessService cardBusinessService;

    /**
     * Создаёт новую карту для указанного пользователя.
     *
     * @param cardDto данные новой карты {@link CardDto}
     * @param userId  ID пользователя, для которого создаётся карта {@link Long}
     * @return ResponseEntity<CardDto> с информацией о созданной карте {@link CardDto}
     */
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

    /**
     * Заблокирует карту по её ID.
     *
     * @param id ID карты, которую нужно заблокировать {@link Long}
     * @return ResponseEntity<CardDto> с обновлённой информацией о карте {@link CardDto}
     */
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

    /**
     * Активирует карту по её ID.
     *
     * @param id ID карты, которую нужно активировать {@link Long}
     * @return ResponseEntity<CardDto> с обновлённой информацией о карте {@link CardDto}
     */
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

    /**
     * Удаляет карту по её ID.
     *
     * @param id ID карты, которую нужно удалить {@link Long}
     * @return ResponseEntity<Long> с ID удалённой карты {@link Long}
     */
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

    /**
     * Возвращает список всех карт, разбитый на страницы.
     *
     * @param page номер страницы (начиная с 0) {@link Integer}
     * @param size количество записей на странице {@link Integer}
     * @return ResponseEntity<Page<CardDto>> со списком карт {@link Page<CardDto>}
     */
    @Operation(summary = "Получить все карты", description = "Возвращает список всех карт, разбитый на страницы.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/cards")
    public ResponseEntity<Page<CardDto>> getAllCardsPaginated(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", example = "10")
            @RequestParam(defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(cardService.getAllCardsPaginated(PageRequest.of(page, size)));
    }

    /**
     * Создаёт нового пользователя.
     *
     * @param userDto данные нового пользователя {@link UserDto}
     * @return ResponseEntity<UserDto> с информацией о созданном пользователе {@link UserDto}
     */
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

    /**
     * Обновляет информацию о пользователе.
     *
     * @param userDto новые данные пользователя {@link UserDto}
     * @param id      ID пользователя, которого нужно обновить {@link Long}
     * @return ResponseEntity<UserDto> с обновлённой информацией о пользователе {@link UserDto}
     */
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

    /**
     * Удаляет пользователя по его ID.
     *
     * @param id ID пользователя, которого нужно удалить {@link Long}
     * @return ResponseEntity<Void> без тела ответа {@link Void}
     */
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

    /**
     * Возвращает пользователя по его ID.
     *
     * @param id ID пользователя, которого нужно найти {@link Long}
     * @return ResponseEntity<UserDto> с информацией о пользователе {@link UserDto}
     */
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

    /**
     * Возвращает список всех пользователей.
     *
     * @return ResponseEntity<List < UserDto>> со списком всех пользователей {@link List<UserDto>}
     */
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
