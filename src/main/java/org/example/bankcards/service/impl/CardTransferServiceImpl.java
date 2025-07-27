package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.example.bankcards.entity.UserEntity;
import org.example.bankcards.enums.CardStatus;
import org.example.bankcards.exception.custom_exceptions.CardIsNotActiveException;
import org.example.bankcards.exception.custom_exceptions.CardNotFoundException;
import org.example.bankcards.exception.custom_exceptions.NegativeBalanceException;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.CardTransferMapper;
import org.example.bankcards.repository.CardTransferRepository;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.service.CardService;
import org.example.bankcards.service.CardTransferService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
@RequiredArgsConstructor
public class CardTransferServiceImpl implements CardTransferService {

    private final CardTransferRepository cardTransferRepository;
    private final UserRepository userRepository;
    private final CardService cardService;
    private final CardTransferMapper cardTransferMapper;

    @Transactional
    @Override
    public CardTransferDto transfer(CardTransferDto cardTransferDto, Long userId) {

        String toCard = cardTransferDto.getCardNumberTo();
        String fromCard = cardTransferDto.getCardNumberFrom();
        BigInteger amount = cardTransferDto.getAmount();

        List<CardDto> cards = cardService.getAllUserCards(userId);
        List<CardDto> existingCards = getExistingUserCardsAndActive(cards, toCard, fromCard);
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found"));

        existingCards.stream()
                .peek(cardDto -> {
                    if (cardDto.getCardNumber().equals(toCard)) {
                        cardDto.setBalance(cardDto.getBalance().add(amount));
                    } else {
                        cardDto.setBalance(cardDto.getBalance().subtract(amount));
                    }
                })
                .peek(cardDto -> {
                    if (cardDto.getBalance().compareTo(BigInteger.ZERO) < 0) {
                        throw new NegativeBalanceException("Negative balance");
                    }
                })
                .forEach(cardService::updateCard);

        return Optional.of(CardTransferEntity.builder()
                        .user(user)
                        .cardNumberTo(toCard)
                        .cardNumberFrom(fromCard)
                        .amount(amount)
                        .transferTime(LocalDateTime.now())
                        .build()
                )
                .map(cardTransferRepository::save)
                .map(cardTransferMapper::toDto)
                .orElseThrow();
    }

    private List<CardDto> getExistingUserCardsAndActive(List<CardDto> cards, String toCard, String fromCard) {
        List<CardDto> result = cards.stream()
                .filter(card ->
                        card.getCardNumber().equals(toCard) || card.getCardNumber().equals(fromCard)
                )
                .peek(card -> {
                            if (!card.getStatus().equals(CardStatus.ACTIVE)) {
                                throw new CardIsNotActiveException("Card is not active");
                            }
                        }
                )
                .toList();

        if (result.size() < 2) {
            throw new CardNotFoundException("Cards not found");
        }

        return result;
    }
}
