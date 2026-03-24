package com.example.translator.mapper.document;

import com.example.translator.entity.DocumentEntity;
import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "fileName", target = "documentName")
    LoadDocumentResponseDto toLoadDto(DocumentEntity documentEntity);

    List<LoadDocumentResponseDto> toLoadDtoList(List<DocumentEntity> documentEntities);
}