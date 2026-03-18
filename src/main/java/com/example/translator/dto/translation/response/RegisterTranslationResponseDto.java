package com.example.translator.dto.translation.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterTranslationResponseDto {
    private String translationId;
    private String detectedSourceLanguage;
    private String targetLanguage;
    private String status;
}
