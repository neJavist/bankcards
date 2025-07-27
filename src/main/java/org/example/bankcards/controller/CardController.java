package org.example.bankcards.controller;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.entity.CardEntity;
import org.example.bankcards.service.CardService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Контроллер для {@link CardEntity}
 */
@RestController
@RequestMapping("/api/cards")
public class CardController {

    private final CardService cardService;

    public CardController(CardService cardService) {
        this.cardService = cardService;
    }

//    @PostMapping
//    public ResponseEntity<CardDto> createCard(@RequestBody CardDto cardDto) {
//        final CardDto createdCard = cardService.createCard(cardDto);
//        return ResponseEntity.status(HttpStatus.CREATED).body(createdCard);
//    }

//    @DeleteMapping("/{id}")
//    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
//        cardService.deleteCard(id);
//        return ResponseEntity.ok().build();
//    }

//    @GetMapping
//    public ResponseEntity<List<CardDto>> getAllCards() {
//        final List<CardDto> cards = cardService.getAllCards();
//        return ResponseEntity.ok(cards);
//    }

//    @GetMapping("/{id}")
//    public ResponseEntity<CardDto> getCardById(@PathVariable Long id) {
//        final CardDto card = cardService.getCard(id);
//        return ResponseEntity.ok(card);
//    }

//    @PutMapping("/{id}")
//    public ResponseEntity<CardDto> updateCard(@RequestBody CardDto cardDto) {
//        final CardDto updatedCard = cardService.updateCard(cardDto);
//        return ResponseEntity.ok(updatedCard);
//    }

    @GetMapping
    public ResponseEntity<List<CardDto>> getAllUserCards(@PathVariable Long userId) {
        return ResponseEntity.ok(cardService.getAllUserCards(userId));
    }

    @PostMapping
    public ResponseEntity<CardTransferDto> transfer() {
        return null;
    }
}
