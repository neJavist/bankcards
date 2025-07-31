package org.example.bankcards.controller;

import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.service.CardBusinessService;
import org.example.bankcards.service.CardService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigInteger;
import java.security.Principal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CardControllerTest {

    private final String username = "testUser";
    private final Long cardId = 1L;
    private final CardTransferDto transferDto = new CardTransferDto();

    private final UserRequestDto userRequestDto = new UserRequestDto();

    @InjectMocks
    private CardController cardController;

    @Mock
    private CardService cardService;
    @Mock
    private CardBusinessService cardBusinessService;

    {
        transferDto.setCardNumberFrom("4567123456789012");
        transferDto.setCardNumberTo("1234567890123456");
        transferDto.setAmount(new java.math.BigInteger("100"));
    }

    @Test
    void testTransfer_Success() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(cardBusinessService.transfer(transferDto, username)).thenReturn(transferDto);

        ResponseEntity<CardTransferDto> response = cardController.transfer(transferDto, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(transferDto, response.getBody());
    }

    @Test
    void testTransfer_SameCard_Exception() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);

        CardTransferDto dto = new CardTransferDto();
        dto.setCardNumberFrom("1234567812345678");
        dto.setCardNumberTo("1234567812345678");
        dto.setAmount(new BigInteger("100"));

        doThrow(new RuntimeException("Карты не могут быть одинаковыми"))
                .when(cardBusinessService).transfer(dto, username);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> cardController.transfer(dto, principal));

        assertEquals("Карты не могут быть одинаковыми", ex.getMessage());
    }

    @Test
    void testTransfer_NegativeBalance_Exception() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(cardBusinessService.transfer(transferDto, username))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Недостаточно средств"));

        assertThrows(ResponseStatusException.class, () -> cardController.transfer(transferDto, principal));
    }

    @Test
    void testGetBalance_Success() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        String expectedBalance = "1000.00";
        when(cardService.getBalance(cardId, username)).thenReturn(expectedBalance);

        ResponseEntity<String> response = cardController.getBalance(cardId, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedBalance, response.getBody());
    }

    @Test
    void testGetBalance_CardNotFound_Exception() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(cardService.getBalance(cardId, username))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Карта не найдена"));

        assertThrows(ResponseStatusException.class, () -> cardController.getBalance(cardId, principal));
    }

    @Test
    void testBlockRequest_Success() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(cardBusinessService.userBlockRequest(cardId, username)).thenReturn(userRequestDto);

        ResponseEntity<UserRequestDto> response = cardController.blockRequest(cardId, principal);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userRequestDto, response.getBody());
    }

    @Test
    void testBlockRequest_CardNotFound_Exception() {
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn(username);
        when(cardBusinessService.userBlockRequest(cardId, username))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Карта не найдена"));

        assertThrows(ResponseStatusException.class, () -> cardController.blockRequest(cardId, principal));
    }
}
