package org.example.bankcards.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.example.bankcards.enums.CardStatus;

import java.time.LocalDateTime;

@Entity
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Table(name = "user_request", schema = "bankcards")
public class UserRequestEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    Long id;

    @Column(name = "user_id")
    Long userId;

    @Column(name = "card_number")
    String cardNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "to_status")
    CardStatus toStatus;

    @Column(name = "request_time")
    LocalDateTime requestTime;
}
