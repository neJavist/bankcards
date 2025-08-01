package org.example.bankcards.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Сущность, представляющая перевод денежных средств между картами.
 */
@Entity
@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "card_transfer", schema = "bankcards")
public class CardTransferEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    UserEntity user;

    @Column(name = "card_number_to")
    String cardNumberTo;

    @Column(name = "card_number_from")
    String cardNumberFrom;

    @Column(name = "transfer_time")
    LocalDateTime transferTime;

    @Column(name = "amount")
    BigInteger amount;

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        CardTransferEntity that = (CardTransferEntity) o;
        return Objects.equals(id, that.id)
                && Objects.equals(cardNumberTo, that.cardNumberTo)
                && Objects.equals(cardNumberFrom, that.cardNumberFrom)
                && Objects.equals(transferTime, that.transferTime)
                && Objects.equals(amount, that.amount);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, cardNumberTo, cardNumberFrom, transferTime, amount);
    }
}
