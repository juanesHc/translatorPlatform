package com.example.translator.dto.person.request;

import com.example.translator.entity.enums.AuthEnum;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrievePersonRequestDto {
    private String givenName;
    private String familyName;
    private String email;
    private AuthEnum authEnum;
    private Boolean activate;
    private LocalDate startDate;
    private LocalDate endDate;
    private int page = 0;
    private int size = 10;
}
