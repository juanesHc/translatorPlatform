package com.example.translator.services.translation;

import com.example.translator.entity.DocumentEntity;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TranslationEntity;
import com.example.translator.entity.enums.LanguagesEnum;
import com.example.translator.entity.enums.PersonRoleEnum;
import com.example.translator.entity.enums.TranslationStatusEnum;
import com.example.translator.dto.translation.request.TranslationRequestDto;
import com.example.translator.dto.translation.response.RegisterTranslationResponseDto;
import com.example.translator.dto.translation.response.TranslationResponseDto;
import com.example.translator.exceptions.TranslationException;
import com.example.translator.repository.DocumentRepository;
import com.example.translator.repository.PersonRepository;
import com.example.translator.repository.TranslationRepository;
import com.example.translator.services.ai.AIService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RegisterTranslationService {

    private final TranslationRepository translationRepository;
    private final DocumentRepository documentRepository;
    private final PersonRepository personRepository;
    private final AIService aiService;

    public RegisterTranslationResponseDto translate(String documentId, TranslationRequestDto requestDto) {

        DocumentEntity documentEntity = documentRepository.findById(UUID.fromString(documentId))
                .orElseThrow(() -> new RuntimeException("Document not found"));

        PersonEntity person = documentEntity.getPerson();

        if (person.getRole().getType() == PersonRoleEnum.COMMON) {
            throw new RuntimeException("No credits available");
        }


        TranslationEntity translationEntity = new TranslationEntity();
        translationEntity.setDocument(documentEntity);
        translationEntity.setTargetLanguage(LanguagesEnum.valueOf(requestDto.getTargetLanguage()));
        translationEntity.setStatus(TranslationStatusEnum.PENDING);
        translationRepository.save(translationEntity);

        try {

            TranslationResponseDto result = aiService.translate(
                    documentEntity.getOriginalText(),
                    LanguagesEnum.valueOf(requestDto.getTargetLanguage())
            );

            translationEntity.setTranslatedText(result.getTranslatedText());
            translationEntity.setSourceLanguage(LanguagesEnum.valueOf(result.getDetectedLanguage()));
            translationEntity.setStatus(TranslationStatusEnum.DONE);
            translationRepository.save(translationEntity);


            return new RegisterTranslationResponseDto(
                    translationEntity.getId().toString(),
                    result.getDetectedLanguage(),
                    String.valueOf(requestDto.getTargetLanguage()),
                    TranslationStatusEnum.DONE.name()
            );

        } catch (TranslationException e) {
            translationEntity.setStatus(TranslationStatusEnum.FAILED);
            translationRepository.save(translationEntity);
            throw new RuntimeException("Translation failed: " + e.getMessage(), e);
        }
    }

}
