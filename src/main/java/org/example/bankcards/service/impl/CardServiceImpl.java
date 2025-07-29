package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.entity.CardEntity;
import org.example.bankcards.entity.UserEntity;
import org.example.bankcards.exception.custom_exceptions.CardNotFoundException;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.CardMapper;
import org.example.bankcards.repository.CardRepository;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigInteger;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    @Transactional
    @Override
    public CardDto createCard(CardDto cardDto, Long userId) {
        return Optional.of(cardDto)
                .map(cardMapper::toEntity)
                .map(entity -> setUserToCardEntity(userId, entity))
                .map(cardRepository::save)
                .map(cardMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    @Transactional
    @Override
    public void deleteCard(Long id) {
        cardRepository.findById(id)
                .ifPresentOrElse(cardRepository::delete, CardNotFoundException::getCardNotFoundException);
    }

    @Transactional
    @Override
    public CardDto updateCard(CardDto cardDto) {
        return cardRepository.findById(cardDto.getId())
                .map(entity -> cardMapper.mergeToEntity(cardDto, entity))
                .map(cardRepository::save)
                .map(cardMapper::toDto)
                .orElseThrow(CardNotFoundException::getCardNotFoundException);
    }

    @Transactional(readOnly = true)
    @Override
    public CardDto getCard(Long id) {
        return cardRepository.findById(id)
                .map(cardMapper::toDto)
                .orElseThrow(CardNotFoundException::getCardNotFoundException);
    }

    @Transactional(readOnly = true)
    @Override
    public List<CardDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(cardMapper::toDto)
                .toList();
    }

    @Override
    public List<CardDto> getAllUserCards(String username) {
        return userRepository.findUserByName(username)
                .map(this::getCardList)
                .orElseThrow(UserNotFoundException::getUserNotFoundException);
    }

    @Override
    public String getBalance(Long cardId, String username) {
        return cardRepository.findByIdAndUserName(cardId, username)
                .map(CardEntity::getBalance)
                .map(BigInteger::toString)
                .orElseThrow(CardNotFoundException::getCardNotFoundException);
    }

    private List<CardDto> getCardList(UserEntity userEntity) {
        return userEntity.getCards().stream()
                .map(cardMapper::toDto)
                .toList();
    }

    private CardEntity setUserToCardEntity(Long userId, CardEntity entity) {
        entity.setUser(userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::getUserNotFoundException));
        return entity;
    }
}

