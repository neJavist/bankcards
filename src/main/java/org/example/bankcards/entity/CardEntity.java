package org.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.enums.CardStatus;

import java.math.BigInteger;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Сущность для таблицы card
 */
@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "card", schema = "bankcards")
public class CardEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    UserEntity user;

    @Column(name = "card_number", unique = true)
    String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    CardStatus status;

    @Column(name = "balance")
    BigInteger balance;

    @Column(name = "expiry_date")
    YearMonth expiryDate;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        CardEntity that = (CardEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(user, that.user)
                && Objects.equals(cardNumber, that.cardNumber)
                && Objects.equals(status, that.status)
                && Objects.equals(balance, that.balance)
                && Objects.equals(expiryDate, that.expiryDate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, user, cardNumber, status, balance, expiryDate);
    }
}
