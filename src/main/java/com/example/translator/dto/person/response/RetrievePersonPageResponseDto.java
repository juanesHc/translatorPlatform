package com.example.translator.dto.person.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePersonPageResponseDto {
    private List<RetrievePersonResponseDto> persons;
    private int currentPage;
    private int totalPages;
    private long totalElements;
    private String message;
}