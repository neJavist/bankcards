package org.example.bankcards.mapper;

import org.example.bankcards.dto.CardDto;
import org.example.bankcards.entity.CardEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CardMapper {

    CardDto toDto(CardEntity card);

    @Mapping(target = "id", ignore = true)
    CardEntity toEntity(CardDto cardDto);

    @Mapping(target = "id", ignore = true)
    CardEntity mergeToEntity(CardDto cardDto, @MappingTarget CardEntity cardEntity);
}

