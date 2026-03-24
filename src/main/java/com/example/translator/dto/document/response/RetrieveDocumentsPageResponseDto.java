package com.example.translator.dto.document.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveDocumentsPageResponseDto {
    private List<LoadDocumentResponseDto> documents;
    private int currentPage;
    private int totalPages;
    private long totalElements;

    private String message;


}
