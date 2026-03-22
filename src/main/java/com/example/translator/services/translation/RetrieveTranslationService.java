package com.example.translator.services.translation;

import com.example.translator.dto.translation.response.RetrieveTranslationResponseDto;
import com.example.translator.entity.TranslationEntity;
import com.example.translator.mapper.translation.TranslationMapper;
import com.example.translator.repository.TranslationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RetrieveTranslationService {

    private final TranslationMapper translationMapper;
    private final TranslationRepository translationRepository;
    private final GenerateDocument generateDocument;

    public List<RetrieveTranslationResponseDto> getTranslationsByDocument(String documentId) {
        List<TranslationEntity> translations = translationRepository
                .findByDocumentId(UUID.fromString(documentId));
        return translationMapper.TranslationEntitiesToRetrieveTranslationsResponseDtos(translations);
    }

    public byte[] getPdfByTranslationId(String translationId) {
        TranslationEntity translation = translationRepository
                .findById(UUID.fromString(translationId))
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        return generateDocument.generatePdfBytes(translation.getTranslatedText());
    }
}
