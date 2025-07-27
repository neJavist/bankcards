package org.example.bankcards.service.impl;

import lombok.RequiredArgsConstructor;
import org.example.bankcards.dto.CardDto;
import org.example.bankcards.exception.custom_exceptions.CardNotFoundException;
import org.example.bankcards.exception.custom_exceptions.UserNotFoundException;
import org.example.bankcards.mapper.CardMapper;
import org.example.bankcards.repository.CardRepository;
import org.example.bankcards.repository.UserRepository;
import org.example.bankcards.service.CardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * Реализация сервиса для работы с банковскими картами.
 * Класс предоставляет методы для создания, удаления, обновления и получения информации о картах.
 *
 * @see CardMapper - маппер для преобразования между DTO и сущностями.
 * @see CardService
 */
@Service
@RequiredArgsConstructor
public class CardServiceImpl implements CardService {

    private final CardMapper cardMapper;
    private final CardRepository cardRepository;
    private final UserRepository userRepository;

    /**
     * Создаёт новую банковскую карту на основе переданных данных.
     *
     * @param cardDto данные карты в формате DTO {@link CardDto}
     * @return сохранённая карта в виде DTO {@link CardDto}
     * @throws RuntimeException если произошла ошибка при обработке {@link RuntimeException}
     */
    @Transactional
    @Override
    public CardDto createCard(CardDto cardDto) {
        return Optional.of(cardDto)
                .map(cardMapper::toEntity)
                .map(cardRepository::save)
                .map(cardMapper::toDto)
                .orElseThrow(RuntimeException::new);
    }

    /**
     * Удаляет карту по её идентификатору.
     *
     * @param id идентификатор удаляемой карты {@link Long}
     * @throws CardNotFoundException если карта не найдена {@link CardNotFoundException}
     */
    @Transactional
    @Override
    public void deleteCard(Long id) {
        cardRepository.findById(id)
                .ifPresentOrElse(cardRepository::delete, this::getCardNotFoundException);
    }

    /**
     * Обновляет информацию существующей карты.
     *
     * @param cardDto обновлённые данные карты в формате DTO {@link CardDto}
     * @return обновлённая карта в виде DTO {@link CardDto}
     * @throws CardNotFoundException если карта не найдена {@link CardNotFoundException}
     */
    @Transactional
    @Override
    public CardDto updateCard(CardDto cardDto) {
        return cardRepository.findById(cardDto.getId())
                .map(entity -> cardMapper.mergeToEntity(cardDto, entity))
                .map(cardRepository::save)
                .map(cardMapper::toDto)
                .orElseThrow(this::getCardNotFoundException);
    }

    /**
     * Получает информацию о карте по её идентификатору.
     *
     * @param id идентификатор карты {@link Long}
     * @return информация о карте в виде DTO {@link CardDto}
     * @throws CardNotFoundException если карта не найдена {@link CardNotFoundException}
     */
    @Transactional(readOnly = true)
    @Override
    public CardDto getCard(Long id) {
        return cardRepository.findById(id)
                .map(cardMapper::toDto)
                .orElseThrow(this::getCardNotFoundException);
    }

    /**
     * Получает список всех карт.
     *
     * @return список карт в виде DTO {@link CardDto}
     */
    @Transactional(readOnly = true)
    @Override
    public List<CardDto> getAllCards() {
        return cardRepository.findAll().stream()
                .map(cardMapper::toDto)
                .toList();
    }

    /**
     * Получает список карт, принадлежащих пользователю с указанным идентификатором.
     *
     * @param id идентификатор пользователя {@link Long}
     * @return список карт пользователя в виде DTO {@link CardDto}
     * @throws UserNotFoundException если пользователь не найден {@link UserNotFoundException}
     */
    @Transactional(readOnly = true)
    @Override
    public List<CardDto> getAllUserCards(Long id) {
        return userRepository.findById(id)
                .map(userEntity ->
                        userEntity.getCards().stream()
                                .map(cardMapper::toDto)
                                .toList()
                )
                .orElseThrow(() -> new UserNotFoundException("User not found"));
    }

    /**
     * @param cardNumber
     * @return
     */
    @Override
    public CardDto getCardByCardNumber(String cardNumber) {
        return cardRepository.findByCardNumber(cardNumber)
                .map(cardMapper::toDto)
                .orElseThrow(this::getCardNotFoundException);
    }

    private CardNotFoundException getCardNotFoundException() {
        return new CardNotFoundException("Card not found");
    }
}

