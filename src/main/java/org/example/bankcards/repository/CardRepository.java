package org.example.bankcards.repository;

import org.example.bankcards.entity.CardEntity;
import org.example.bankcards.entity.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigInteger;
import java.util.Optional;

/**
 * Интерфейс {@code CardRepository} предоставляет методы для взаимодействия с базой данных,
 * связанные с банковскими картами.
 */
public interface CardRepository extends JpaRepository<CardEntity, Long> {

    /**
     * Находит карту по её идентификатору и имени пользователя (владельца).
     *
     * @param id       идентификатор карты
     * @param userName имя пользователя (владельца карты)
     * @return {@code Optional<CardEntity>} — опциональный объект, содержащий найденную карту или пустое значение
     */
    Optional<CardEntity> findByIdAndUserName(Long id, String userName);

    /**
     * Возвращает список всех карт, принадлежащих указанному пользователю, в виде страницы.
     *
     * @param user     пользователь, чьи карты нужно получить
     * @param pageable параметры пагинации (номер страницы, количество записей на странице)
     * @return {@code Page<CardEntity>} — страница с картами пользователя
     */
    Page<CardEntity> findAllByUser(UserEntity user, Pageable pageable);

    /**
     * Выполняет фильтрацию карт по различным критериям: номеру карты, статусу, балансу, сроку действия и идентификатору пользователя.
     * <p>
     * Используется нативный SQL-запрос для гибкой фильтрации. Параметры, переданные как {@code null}, игнорируются в условии WHERE.
     *
     * @param cardNumber   номер карты (опционально)
     * @param status       статус карты (опционально)
     * @param balance      баланс карты (опционально)
     * @param expiryDate   срок действия карты в формате "ГГГГ-ММ" (опционально)
     * @param userId       идентификатор пользователя, чьи карты нужно получить
     * @param pageable     параметры пагинации (номер страницы, количество записей на странице)
     * @return {@code Page<CardEntity>} — страница с отфильтрованными картами
     */
    @Query(value = """
            SELECT * FROM card
            WHERE (:cardNumber IS NULL OR card_number = :cardNumber)
              AND (:status IS NULL OR status = :status)
              AND (:balance IS NULL OR balance = :balance)
              AND (:expiryDate IS NULL OR expiry_date = :expiryDate)
            AND user_id = :userId
            """,
            countQuery = """
                    SELECT COUNT(*) FROM card
                    WHERE (:cardNumber IS NULL OR card_number = :cardNumber)
                      AND (:status IS NULL OR status = :status)
                      AND (:balance IS NULL OR balance = :balance)
                      AND (:expiryDate IS NULL OR expiry_date = :expiryDate)
                    AND user_id = :userId
                    """,
            nativeQuery = true)
    Page<CardEntity> findAllByCardNumberAndStatusAndBalanceAndExpiryDateAndUser(
            @Param("cardNumber") String cardNumber,
            @Param("status") String status,
            @Param("balance") BigInteger balance,
            @Param("expiryDate") String expiryDate,
            @Param("userId") Long userId,
            Pageable pageable
    );
}
