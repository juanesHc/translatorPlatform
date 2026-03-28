package com.example.translator.dto.person.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePersonResponseDto {
    private String personId;
    private String givenName;
    private String familyName;
    private String email;
    private String role;
    private String authEnum;
    private boolean activate;
    private LocalDateTime createdAt;
}