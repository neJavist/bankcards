package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardTransferDto;
import org.example.bankcards.entity.CardTransferEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface CardTransferMapper {

    CardTransferDto toDto(CardTransferEntity cardTransferEntity);

    @Mapping(target = "id", ignore = true)
    CardTransferEntity toEntity(CardTransferDto cardTransferDto);

    List<CardTransferDto> toListDto(List<CardTransferEntity> cardTransferEntities);

    @Mapping(target = "id", ignore = true)
    CardTransferEntity mergeToEntity(CardTransferDto cardTransferDto,
                                     @MappingTarget CardTransferEntity cardTransferEntity);
}
