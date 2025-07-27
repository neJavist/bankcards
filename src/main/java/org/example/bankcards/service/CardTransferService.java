package org.example.bankcards.service;

import org.example.bankcards.dto.CardTransferDto;

import java.math.BigInteger;

public interface CardTransferService {
    CardTransferDto transfer(CardTransferDto cardTransferDto, Long userId);
}
