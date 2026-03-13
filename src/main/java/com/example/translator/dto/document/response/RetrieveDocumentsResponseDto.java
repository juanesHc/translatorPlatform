package com.example.translator.dto.document.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
public class RetrieveDocumentsResponseDto {
    private String documentId;
    private String documentMimeType;
    private String documentName;
    private LocalDateTime documentCreation;
    private String documentContentBase64;
}
