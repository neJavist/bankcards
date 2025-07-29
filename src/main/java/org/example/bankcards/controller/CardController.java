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
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.service.CardBusinessService;
import org.example.bankcards.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CardController {

    private final CardService cardService;
    private final CardBusinessService cardBusinessService;

    @Operation(
            summary = "Получить все карты",
            description = "Возвращает список всех карт, принадлежащих текущему авторизованному пользователю."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Список карт",
                    content = {@Content(mediaType = "application/json", schema = @Schema(implementation = List.class))}),
            @ApiResponse(responseCode = "401", description = "Неавторизованный доступ"),
            @ApiResponse(responseCode = "404", description = "Карты не найдены"),
            @ApiResponse(responseCode = "500", description = "Ошибка сервера")
    })
    @GetMapping
    public ResponseEntity<List<CardDto>> getAllUserCards(Principal principal) {
        return ResponseEntity.ok(cardService.getAllUserCards(principal.getName()));
    }

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
            Principal principal) {
        return ResponseEntity.ok(cardBusinessService.transfer(cardTransferDto, principal.getName()));
    }

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
            Principal principal) {
        return ResponseEntity.ok(cardService.getBalance(id, principal.getName()));
    }

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
            Principal principal) {
        return ResponseEntity.ok(cardBusinessService.userBlockRequest(id, principal.getName()));
    }
}
