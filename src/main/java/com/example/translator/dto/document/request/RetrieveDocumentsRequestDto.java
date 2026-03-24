package com.example.translator.dto.document.request;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class RetrieveDocumentsRequestDto {

    private String originalText;
    private LocalDate createdAt;
    private String fileName;
    private LocalDate targetDate=LocalDate.now();
    private LocalDate sourceDate;

    private int page = 0;
    private int size = 10;


}
