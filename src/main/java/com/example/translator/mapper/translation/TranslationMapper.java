package com.example.translator.mapper.translation;

import com.example.translator.dto.translation.response.RetrieveTranslationResponseDto;
import com.example.translator.entity.TranslationEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface TranslationMapper {

    @Mapping(source = "id", target = "translationId")
    @Mapping(source = "sourceLanguage", target = "sourceLanguage")
    @Mapping(source = "targetLanguage", target = "targetLanguage")
    @Mapping(source = "status", target = "status")
    @Mapping(source = "createdAt", target = "createdAt")
    RetrieveTranslationResponseDto TranslationEntityToRetrieveTranslationResponseDto(TranslationEntity entity);

    List<RetrieveTranslationResponseDto> TranslationEntitiesToRetrieveTranslationsResponseDtos(List<TranslationEntity> entities);
}