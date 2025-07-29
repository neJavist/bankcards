package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CardTransferMapper {

    CardTransferDto toDto(CardTransferEntity cardTransferEntity);

    @Mapping(target = "id", ignore = true)
    CardTransferEntity toEntity(CardTransferDto cardTransferDto);
}
