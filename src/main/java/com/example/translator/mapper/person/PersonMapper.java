package com.example.translator.mapper.person;

import com.example.translator.dto.person.response.RetrieveMyDataResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.dto.person.request.RegisterPersonRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "credits", constant = "2")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "documentEntities", ignore = true)
    PersonEntity RegisterPersonRequestDtoToPersonEntity(RegisterPersonRequestDto dto);

    @Mapping(source = "givenName", target = "firstName")
    @Mapping(source = "familyName", target = "lastName")
    RetrieveMyDataResponseDto toRetrieveMyDataResponseDto(PersonEntity personEntity);

}
