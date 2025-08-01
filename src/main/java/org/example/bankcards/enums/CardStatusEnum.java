package org.example.bankcards.enums;

/**
 * Перечисление определяет возможные статусы банковской карты.
 * <p>
 * Используется для указания текущего состояния карты в системе. Каждый элемент перечисления представляет собой уникальное состояние:
 * - {@code ACTIVE}: карта активна и может использоваться для операций;
 * - {@code BLOCKED}: карта заблокирована, дальнейшие операции невозможны;
 * - {@code EXPIRED}: карта истекла по сроку действия.
 */
public enum CardStatusEnum {
    ACTIVE,
    BLOCKED,
    EXPIRED
}

