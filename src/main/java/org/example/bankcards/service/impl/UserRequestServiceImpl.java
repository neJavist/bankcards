package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.enums.CardStatus;
import org.example.bankcards.exception.custom_exceptions.CardIsNotActiveException;
import org.example.bankcards.exception.custom_exceptions.CardNotFoundException;
import org.example.bankcards.mapper.UserRequestMapper;
import org.example.bankcards.repository.UserRequestRepository;
import org.example.bankcards.service.CardService;
import org.example.bankcards.service.UserRequestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserRequestServiceImpl implements UserRequestService {

    private final UserRequestMapper userRequestMapper;
    private final UserRequestRepository userRequestRepository;
    private final CardService cardService;

    @Transactional
    @Override
    public UserRequestDto userRequest(UserRequestDto userRequestDto, Long userId) {
        return cardService.getAllUserCards(userId).stream()
                .filter(card -> card.getCardNumber().equals(userRequestDto.getCardNumber()))
                .findAny()
                .map(this::checkIsBlocked)
                .map(foundCard -> {
                    userRequestDto.setRequestTime(LocalDateTime.now());
                    userRequestDto.setUserId(userId);
                    userRequestDto.setToStatus(CardStatus.BLOCKED);
                    return userRequestDto;
                })
                .map(userRequestMapper::toEntity)
                .map(userRequestRepository::save)
                .map(userRequestMapper::toDto)
                .orElseThrow(() -> new CardNotFoundException("User does not have a suitable card"));
    }

    private CardDto checkIsBlocked(CardDto card) {
        if (card.getStatus().equals(CardStatus.BLOCKED)) {
            throw new CardIsNotActiveException("Card already BLOCKED!");
        }
        return card;
    }
}
