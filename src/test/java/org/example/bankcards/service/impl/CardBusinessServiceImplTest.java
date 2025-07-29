package org.example.bankcards.service.impl;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.example.bankcards.entity.UserEntity;
import org.example.bankcards.entity.UserRequestEntity;
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
import org.example.bankcards.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardBusinessServiceImplTest {

    private final String username = "testUser";
    private final String cardNumberFrom = "4567123456789012";
    private final String cardNumberTo = "1234567890123456";
    private final BigInteger amount = new BigInteger("100");

    private final CardTransferEntity transferEntity = CardTransferEntity.builder()
            .cardNumberFrom(cardNumberFrom)
            .cardNumberTo(cardNumberTo)
            .amount(amount)
            .build();

    private final CardTransferDto transferDto = new CardTransferDto();

    private final UserEntity userEntity = UserEntity.builder()
            .id(1L)
            .name(username)
            .build();

    private final CardDto activeCard = CardDto.builder()
            .id(1L)
            .cardNumber(cardNumberFrom)
            .balance(new BigInteger("500"))
            .status(CardStatusEnum.ACTIVE)
            .build();

    private final CardDto anotherActiveCard = CardDto.builder()
            .id(2L)
            .cardNumber(cardNumberTo)
            .balance(new BigInteger("200"))
            .status(CardStatusEnum.ACTIVE)
            .build();

    @InjectMocks
    private CardBusinessServiceImpl cardBusinessService;

    @Mock
    private CardService cardService;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserRequestRepository userRequestRepository;
    @Mock
    private CardTransferRepository cardTransferRepository;
    @Mock
    private CardTransferMapper cardTransferMapper;
    @Mock
    private UserRequestMapper userRequestMapper;

    {
        transferDto.setCardNumberFrom(cardNumberFrom);
        transferDto.setCardNumberTo(cardNumberTo);
        transferDto.setAmount(amount);
    }

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testBlockCard_Success() {
        CardDto card = CardDto.builder().id(1L).status(CardStatusEnum.ACTIVE).build();
        when(cardService.getCard(1L)).thenReturn(card);
        when(cardService.updateCard(card)).thenReturn(card);

        CardDto result = cardBusinessService.blockCard(1L);

        assertNotNull(result);
        assertEquals(CardStatusEnum.BLOCKED, result.getStatus());
    }

    @Test
    void testActivateCard_Success() {
        CardDto card = CardDto.builder().id(1L).status(CardStatusEnum.BLOCKED).build();
        when(cardService.getCard(1L)).thenReturn(card);
        when(cardService.updateCard(card)).thenReturn(card);

        CardDto result = cardBusinessService.activateCard(1L);

        assertNotNull(result);
        assertEquals(CardStatusEnum.ACTIVE, result.getStatus());
    }

    @Test
    void testTransfer_SuccessfulTransfer() {
        List<CardDto> cards = List.of(activeCard, anotherActiveCard);
        when(cardService.getAllUserCards(username)).thenReturn(cards);
        when(userRepository.findUserByName(username)).thenReturn(Optional.of(userEntity));
        when(cardTransferMapper.toDto(any())).thenReturn(transferDto);
        when(cardTransferRepository.save(any())).thenReturn(transferEntity);

        CardTransferDto result = cardBusinessService.transfer(transferDto, username);

        assertNotNull(result);
        assertEquals(transferDto.getCardNumberFrom(), result.getCardNumberFrom());
        assertEquals(transferDto.getCardNumberTo(), result.getCardNumberTo());
        assertEquals(transferDto.getAmount(), result.getAmount());

        verify(cardService, times(2)).updateCard(any());
        verify(cardTransferRepository, times(1)).save(any());
    }

    @Test
    void testTransfer_SameCard_Exception() {
        CardTransferDto dto = new CardTransferDto();
        dto.setCardNumberFrom("1234");
        dto.setCardNumberTo("1234");

        assertThrows(RuntimeException.class, () -> cardBusinessService.transfer(dto, username));
    }

    @Test
    void testTransfer_NegativeBalance_Exception() {
        CardDto fromCard = CardDto.builder()
                .cardNumber(cardNumberFrom)
                .balance(new BigInteger("50"))
                .status(CardStatusEnum.ACTIVE)
                .build();

        CardDto toCard = CardDto.builder()
                .cardNumber(cardNumberTo)
                .balance(new BigInteger("200"))
                .status(CardStatusEnum.ACTIVE)
                .build();

        List<CardDto> cards = List.of(fromCard, toCard);

        UserEntity user = UserEntity.builder()
                .id(1L)
                .name(username)
                .build();

        when(cardService.getAllUserCards(username)).thenReturn(cards);
        when(userRepository.findUserByName(username)).thenReturn(Optional.of(user));

        CardTransferDto transferDto = CardTransferDto.builder()
                .cardNumberFrom(cardNumberFrom)
                .cardNumberTo(cardNumberTo)
                .amount(new BigInteger("100"))
                .build();

        assertThrows(NegativeBalanceException.class, () ->
                cardBusinessService.transfer(transferDto, username));
    }


    @Test
    void testTransfer_UserNotFound_Exception() {
        CardDto fromCard = CardDto.builder()
                .cardNumber(cardNumberFrom)
                .balance(new BigInteger("100"))
                .status(CardStatusEnum.ACTIVE)
                .build();

        CardDto toCard = CardDto.builder()
                .cardNumber(cardNumberTo)
                .balance(new BigInteger("200"))
                .status(CardStatusEnum.ACTIVE)
                .build();

        List<CardDto> cards = List.of(fromCard, toCard);

        when(cardService.getAllUserCards(username)).thenReturn(cards);

        when(userRepository.findUserByName(username)).thenReturn(Optional.empty());

        CardTransferDto transferDto = CardTransferDto.builder()
                .cardNumberFrom(cardNumberFrom)
                .cardNumberTo(cardNumberTo)
                .amount(new BigInteger("50"))
                .build();

        assertThrows(UserNotFoundException.class, () ->
                cardBusinessService.transfer(transferDto, username));
    }

    @Test
    void testTransfer_InactiveCard_Exception() {
        CardDto inactiveCard = CardDto.builder()
                .cardNumber(cardNumberFrom)
                .balance(new BigInteger("500"))
                .status(CardStatusEnum.BLOCKED)
                .build();
        CardDto activeCard = CardDto.builder()
                .cardNumber(cardNumberTo)
                .balance(new BigInteger("200"))
                .status(CardStatusEnum.ACTIVE)
                .build();
        List<CardDto> cards = List.of(inactiveCard, activeCard);

        when(cardService.getAllUserCards(username)).thenReturn(cards);

        assertThrows(CardIsNotActiveException.class, () -> cardBusinessService.transfer(transferDto, username));
    }

    @Test
    void testTransfer_UserHasNotEnoughCards_Exception() {
        List<CardDto> cards = List.of(activeCard);
        when(cardService.getAllUserCards(username)).thenReturn(cards);

        assertThrows(CardNotFoundException.class, () -> cardBusinessService.transfer(transferDto, username));
    }

    @Test
    void testUserBlockRequest_Success() {
        String username = "testUser";
        Long cardId = 1L;
        String cardNumber = "4567123456789012";

        CardDto card = CardDto.builder()
                .id(cardId)
                .cardNumber(cardNumber)
                .status(CardStatusEnum.ACTIVE)
                .build();

        List<CardDto> userCards = List.of(card);

        UserEntity userEntity = UserEntity.builder()
                .id(1L)
                .name(username)
                .build();

        UserRequestDto expectedDto = UserRequestDto.builder()
                .cardNumber(cardNumber)
                .userId(userEntity.getId())
                .toStatus(CardStatusEnum.BLOCKED)
                .build();

        UserRequestEntity userRequestEntity = UserRequestEntity.builder()
                .id(1L)
                .cardNumber(card.getCardNumber())
                .toStatus(CardStatusEnum.BLOCKED)
                .requestTime(LocalDateTime.now())
                .build();

        when(cardService.getCard(cardId)).thenReturn(card);
        when(cardService.getAllUserCards(username)).thenReturn(userCards);
        when(userRepository.findUserByName(username)).thenReturn(Optional.of(userEntity));
        when(cardService.updateCard(any())).thenAnswer(inv -> inv.getArgument(0));
        when(userRequestMapper.toEntity(any())).thenReturn(userRequestEntity);
        when(userRequestRepository.save(any())).thenReturn(userRequestEntity);
        when(userRequestMapper.toDto(any())).thenReturn(expectedDto);

        UserRequestDto result = cardBusinessService.userBlockRequest(cardId, username);

        assertNotNull(result);
        assertEquals(cardNumber, result.getCardNumber());
        assertEquals(CardStatusEnum.BLOCKED, result.getToStatus());
        assertEquals(userEntity.getId(), result.getUserId());
    }


    @Test
    void testUserBlockRequest_CardAlreadyBlocked_Exception() {
        CardDto card = CardDto.builder()
                .id(1L)
                .cardNumber(cardNumberFrom)
                .status(CardStatusEnum.BLOCKED)
                .build();

        when(cardService.getCard(1L)).thenReturn(card);
        when(cardService.getAllUserCards(username)).thenReturn(List.of(card));

        assertThrows(CardIsNotActiveException.class, () -> cardBusinessService.userBlockRequest(1L, username));
    }

    @Test
    void testUserBlockRequest_CardNotFound_Exception() {
        when(cardService.getCard(1L)).thenThrow(CardNotFoundException.class);

        assertThrows(CardNotFoundException.class, () -> cardBusinessService.userBlockRequest(1L, username));
    }
}
