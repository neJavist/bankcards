package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * Mapper для преобразования объектов класса {@link CardTransferEntity} в {@link CardTransferDto} и наоборот.
 */
@Mapper(componentModel = "spring")
public interface CardTransferMapper {

    /**
     * Преобразует сущность {@link CardTransferEntity} в DTO-объект {@link CardTransferDto}.
     *
     * @param cardTransferEntity сущность перевода средств {@link CardTransferEntity}
     * @return DTO-объект, представляющий перевод средств {@link CardTransferDto}
     */
    CardTransferDto toDto(CardTransferEntity cardTransferEntity);

    /**
     * Преобразует DTO-объект {@link CardTransferDto} в сущность {@link CardTransferEntity}.
     * <p>
     * Поле {@code id} игнорируется при преобразовании.
     *
     * @param cardTransferDto DTO-объект перевода средств {@link CardTransferDto}
     * @return сущность перевода средств {@link CardTransferEntity}
     */
    @Mapping(target = "id", ignore = true)
    CardTransferEntity toEntity(CardTransferDto cardTransferDto);
}
