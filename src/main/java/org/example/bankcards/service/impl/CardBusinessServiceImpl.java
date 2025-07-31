package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.example.bankcards.entity.UserEntity;
import org.example.bankcards.enums.CardStatusEnum;
import org.example.bankcards.exception.custom_exceptions.CardIsNotActiveException;
import org.example.bankcards.exception.custom_exceptions.CardNotFoundException;
import org.example.bankcards.exception.custom_exceptions.NegativeBalanceException;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.CardTransferMapper;
import org.example.bankcards.mapper.UserRequestMapper;
import org.example.bankcards.repository.CardTransferRepository;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.repository.UserRequestRepository;
import org.example.bankcards.service.CardBusinessService;
import org.example.bankcards.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardBusinessServiceImpl implements CardBusinessService {

    private final CardService cardService;
    private final CardTransferMapper cardTransferMapper;
    private final UserRequestMapper userRequestMapper;
    private final UserRequestRepository userRequestRepository;
    private final CardTransferRepository cardTransferRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CardDto blockCard(Long id) {
        CardDto cardDto = cardService.getCardById(id);
        cardDto.setStatus(CardStatusEnum.BLOCKED);
        return cardService.updateCard(cardDto);
    }

    @Transactional
    @Override
    public CardDto activateCard(Long id) {
        CardDto cardDto = cardService.getCardById(id);
        cardDto.setStatus(CardStatusEnum.ACTIVE);
        return cardService.updateCard(cardDto);
    }

    @Transactional(isolation = Isolation.SERIALIZABLE)
    @Override
    public CardTransferDto transfer(CardTransferDto cardTransferDto, String username) {

        String toCard = cardTransferDto.getCardNumberTo();
        String fromCard = cardTransferDto.getCardNumberFrom();

        if (toCard.equals(fromCard)) {
            throw new RuntimeException("Карты не могут быть одинаковыми");
        }

        BigInteger amount = cardTransferDto.getAmount();

        List<CardDto> cards = cardService.getAllUserCards(username);
        List<CardDto> existingCards = getExistingUserCardsAndActive(cards, toCard, fromCard);
        UserEntity user = userRepository.findUserByName(username)
                .orElseThrow(UserNotFoundException::getUserNotFoundException);

        existingCards.stream()
                .peek(cardDto -> transferBalance(cardDto, toCard, amount))
                .peek(this::checkNegativeBalance)
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

    private void checkNegativeBalance(CardDto cardDto) {
        if (cardDto.getBalance().compareTo(BigInteger.ZERO) < 0) {
            throw NegativeBalanceException.getNegativeBalanceException();
        }
    }

    private void transferBalance(CardDto cardDto, String toCard, BigInteger amount) {
        if (cardDto.getCardNumber().equals(toCard)) {
            cardDto.setBalance(cardDto.getBalance().add(amount));
        } else {
            cardDto.setBalance(cardDto.getBalance().subtract(amount));
        }
    }

    private List<CardDto> getExistingUserCardsAndActive(List<CardDto> cards,
                                                        String toCard,
                                                        String fromCard) {
        List<CardDto> result = cards.stream()
                .filter(card -> checkingUserHasCards(toCard, fromCard, card))
                .peek(this::checkIsActiveCard)
                .toList();

        if (result.size() < 2) {
            throw CardNotFoundException.getCardNotFoundException();
        }

        return result;
    }

    private boolean checkingUserHasCards(String toCard, String fromCard, CardDto card) {
        return card.getCardNumber().equals(toCard) || card.getCardNumber().equals(fromCard);
    }

    private void checkIsActiveCard(CardDto card) {
        if (!card.getStatus().equals(CardStatusEnum.ACTIVE)) {
            throw CardIsNotActiveException.getCardIsNotActiveException();
        }
    }

    @Transactional
    @Override
    public UserRequestDto userBlockRequest(Long id, String username) {
        return cardService.getAllUserCards(username).stream()
                .filter(card ->
                        card.getCardNumber().equals(cardService.getCardById(id).getCardNumber()))
                .findAny()
                .map(this::checkIsBlocked)
                .map(card -> {
                    card.setStatus(CardStatusEnum.BLOCKED);
                    cardService.updateCard(card);
                    return UserRequestDto.builder()
                            .requestTime(LocalDateTime.now())
                            .userId(userRepository.findUserByName(username).orElseThrow().getId())
                            .toStatus(CardStatusEnum.BLOCKED)
                            .cardNumber(card.getCardNumber())
                            .build();
                })
                .map(userRequestMapper::toEntity)
                .map(userRequestRepository::save)
                .map(userRequestMapper::toDto)
                .orElseThrow(CardNotFoundException::getCardNotFoundException);
    }

    private CardDto checkIsBlocked(CardDto card) {
        if (card.getStatus().equals(CardStatusEnum.BLOCKED)) {
            throw CardIsNotActiveException.getCardIsNotActiveException();
        }
        return card;
    }
}
