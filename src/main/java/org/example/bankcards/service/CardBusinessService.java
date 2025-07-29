package org.example.bankcards.service;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;

public interface CardBusinessService {
    CardDto blockCard(Long id);

    CardDto activateCard(Long id);

    CardTransferDto transfer(CardTransferDto cardTransferDto, String username);

    UserRequestDto userBlockRequest(Long id, String username);
}
