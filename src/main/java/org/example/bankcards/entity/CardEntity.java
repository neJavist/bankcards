package org.example.bankcards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.enums.CardStatusEnum;

import java.math.BigInteger;
import java.time.YearMonth;
import java.util.Objects;

/**
 * Сущность, представляющая банковскую карту в системе.
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
    CardStatusEnum status;

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
