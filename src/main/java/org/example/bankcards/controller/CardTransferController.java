package org.example.bankcards.controller;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.service.CardTransferService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/card-transfer/")
@RequiredArgsConstructor
public class CardTransferController {

    private final CardTransferService cardTransferService;

    @PostMapping("/user/{userId}")
    public ResponseEntity<CardTransferDto> transfer(@RequestBody CardTransferDto cardTransferDto,
                                                    @PathVariable Long userId) {
        final CardTransferDto cardTransfer =
                cardTransferService.transfer(cardTransferDto, userId);
        return ResponseEntity.ok(cardTransfer);
    }
}
