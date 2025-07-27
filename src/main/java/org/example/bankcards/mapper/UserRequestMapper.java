package org.example.bankcards.mapper;

import org.example.bankcards.dto.UserRequestDto;
import org.example.bankcards.entity.UserRequestEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserRequestMapper {

    UserRequestDto toDto(UserRequestEntity userRequestEntity);

    @Mapping(target = "id", ignore = true)
    UserRequestEntity toEntity(UserRequestDto userRequestDto);

    List<UserRequestDto> toListDto(List<UserRequestEntity> userRequestEntities);

    @Mapping(target = "id", ignore = true)
    UserRequestEntity mergeToEntity(UserRequestDto userRequestDto,
                                    @MappingTarget UserRequestEntity userRequestEntity);
}
