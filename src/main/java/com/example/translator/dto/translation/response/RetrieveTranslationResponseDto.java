package com.example.translator.dto.translation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetrieveTranslationResponseDto {
    private String translationId;
    private String sourceLanguage;
    private String targetLanguage;
    private String status;
    private LocalDateTime createdAt;
}
