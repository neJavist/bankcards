package org.example.bankcards.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.util.Objects;

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
