package com.example.translator.mapper.document;

import com.example.translator.entity.DocumentEntity;
import com.example.translator.dto.document.response.LoadDocumentResponseDto;
import com.example.translator.dto.document.response.RetrieveDocumentsResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DocumentMapper {

    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "mimeType", target = "documentMimeType")
    @Mapping(source = "fileName", target = "documentName")
    @Mapping(source = "createdAt", target = "documentCreation")
    @Mapping(source = "contentBase64", target = "documentContentBase64")
    RetrieveDocumentsResponseDto DocumentEntityToRetrieveDocumentsResponseDto(DocumentEntity documentEntity);

    List<RetrieveDocumentsResponseDto> DocumentEntitiesToRetrieveDocumentsResponseDtos(List<DocumentEntity> documentEntities);

    @Mapping(source = "id", target = "documentId")
    @Mapping(source = "fileName", target = "documentName")
    LoadDocumentResponseDto DocumentEntityToLoadDocumentResponseDto(DocumentEntity documentEntity);

    List<LoadDocumentResponseDto> DocumentEntitiesToLoadDocumentResponseDtos(List<DocumentEntity> documentEntities);
}