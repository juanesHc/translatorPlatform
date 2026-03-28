package com.example.translator.mapper.person;

import com.example.translator.dto.person.request.RegisterClassicPersonRequestDto;
import com.example.translator.dto.person.request.RegisterGooglePersonRequestDto;
import com.example.translator.dto.person.response.RetrieveMyDataResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.enums.AuthEnum;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface PersonMapper {

    @Mapping(target = "activate", ignore = true)
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "documentEntities", ignore = true)
    PersonEntity RegisterPersonGoogleRequestDtoToPersonEntity(RegisterGooglePersonRequestDto dto);

    @Mapping(source = "givenName", target = "firstName")
    @Mapping(source = "familyName", target = "lastName")
    RetrieveMyDataResponseDto toRetrieveMyDataResponseDto(PersonEntity personEntity);

    @Mapping(source = "givenName", target = "givenName")
    @Mapping(source = "familyName", target = "familyName")
    @Mapping(source = "email", target = "email")
    @Mapping(target = "activate", constant = "false")
    @Mapping(target = "authEnum", constant = "CLASSIC")
    @Mapping(target = "role", ignore = true)
    @Mapping(target = "documentEntities", ignore = true)
    @Mapping(target = "password", ignore = true)
    PersonEntity toEntityFromClassicRegister(RegisterClassicPersonRequestDto dto);

}
