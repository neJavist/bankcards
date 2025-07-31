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
import org.example.bankcards.dto.CardFilterDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.service.CardBusinessService;
import org.example.bankcards.service.CardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

/**
 * REST-контроллер для работы с картами.
 */
@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardBusinessService cardBusinessService;

    /**
     * Возвращает список всех карт, принадлежащих текущему авторизованному пользователю,
     * разбитый на страницы.
     *
     * @param page      номер страницы (начиная с 0) {@link Integer}
     * @param size      количество записей на странице {@link Integer}
     * @param principal объект Principal, содержащий имя авторизованного пользователя {@link Principal}
     * @return ResponseEntity<Page < CardDto>> — страница с картами пользователя {@link Page<CardDto>}
     */
    @Operation(
            summary = "Получить все карты",
            description = "Возвращает список всех карт, принадлежащих текущему авторизованному пользователю, разбитый на страницы."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "404", description = "Карты не найдены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<Page<CardDto>> getAllUserCardsPaginated(
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", example = "10")
            @RequestParam(defaultValue = "10") int size,
            Principal principal
    ) {
        return ResponseEntity.ok(
                cardService.getAllUserCardsPaged(principal.getName(), PageRequest.of(page, size))
        );
    }

    /**
     * Выполняет перевод средств с одной карты на другую.
     *
     * @param cardTransferDto объект, содержащий данные перевода {@link CardTransferDto}
     * @param principal       объект Principal, содержащий имя авторизованного пользователя {@link Principal}
     * @return ResponseEntity<CardTransferDto> — информация о выполненном переводе {@link CardTransferDto}
     */
    @Operation(
            summary = "Перевод средств",
            description = "Выполняет перевод средств с одной карты на другую."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Перевод выполнен успешно",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = CardTransferDto.class))}),
            @ApiResponse(responseCode = "400", description = "Некорректные данные перевода"),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping
    public ResponseEntity<CardTransferDto> transfer(
            @Valid @RequestBody CardTransferDto cardTransferDto,
            Principal principal
    ) {
        return ResponseEntity.ok(cardBusinessService.transfer(cardTransferDto, principal.getName()));
    }

    /**
     * Возвращает текущий баланс указанной карты.
     *
     * @param id        идентификатор карты {@link Long}
     * @param principal объект Principal, содержащий имя авторизованного пользователя {@link Principal}
     * @return ResponseEntity<String> — строковое представление баланса {@link String}
     */
    @Operation(
            summary = "Получить баланс карты",
            description = "Возвращает текущий баланс указанной карты."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Баланс карты",
                    content = {@Content(mediaType = "text/plain", schema = @Schema(type = "string"))}),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена или не принадлежит пользователю"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping("/{id}/balance")
    public ResponseEntity<String> getBalance(
            @Parameter(description = "ID карты", example = "1") @PathVariable Long id,
            Principal principal
    ) {
        return ResponseEntity.ok(cardService.getBalance(id, principal.getName()));
    }

    /**
     * Отправляет запрос администратору на блокировку указанной карты.
     *
     * @param id        идентификатор карты {@link Long}
     * @param principal объект Principal, содержащий имя авторизованного пользователя {@link Principal}
     * @return ResponseEntity<UserRequestDto> — информация о созданном запросе на блокировку {@link UserRequestDto}
     */
    @Operation(
            summary = "Запрос на блокировку карты",
            description = "Отправляет запрос администратору на блокировку карты."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Запрос на блокировку отправлен",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = UserRequestDto.class))}),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "404", description = "Карта не найдена"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/{id}/block-request")
    public ResponseEntity<UserRequestDto> blockRequest(
            @Parameter(description = "ID карты", example = "1") @PathVariable Long id,
            Principal principal
    ) {
        return ResponseEntity.ok(cardBusinessService.userBlockRequest(id, principal.getName()));
    }

    /**
     * Обрабатывает POST-запрос для получения страницы с картами пользователя с фильтрацией.
     * <p>
     * Метод принимает DTO с критериями фильтрации, номер страницы и количество записей на странице,
     * а также информацию о текущем авторизованном пользователе. Возвращает отфильтрованный список карт в виде страницы.
     *
     * @param cardFilterDto объект с критериями фильтрации (например, статус карты, диапазон баланса и т.д.) {@link CardFilterDto}
     * @param page          номер страницы (начинается с 0) {@link Integer}
     * @param size          количество записей на странице {@link Integer}
     * @param principal     информация о текущем авторизованном пользователе {@link Principal}
     * @return ResponseEntity<Page < CardDto>> — страница с отфильтрованными картами пользователя {@link Page<CardDto>}
     */
    @Operation(
            summary = "Получить все карты пользователя по параметрам",
            description = "Возвращает список всех карт, принадлежащих текущему авторизованному пользователю по параметрам карты, разбитый на страницы."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @PostMapping("/filter")
    public ResponseEntity<Page<CardDto>> getUserCardsFiltredPaged(
            @Valid @RequestBody CardFilterDto cardFilterDto,
            @Parameter(description = "Номер страницы", example = "0")
            @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Количество записей на странице", example = "10")
            @RequestParam(defaultValue = "10") int size,
            Principal principal
    ) {
        return ResponseEntity.ok(cardService.getUserCardsFiltredPaged(
                cardFilterDto,
                principal.getName(),
                PageRequest.of(page, size)
        ));
    }
}
