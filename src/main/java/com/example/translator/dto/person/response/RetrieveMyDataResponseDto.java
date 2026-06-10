package com.example.translator.dto.person.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RetrieveMyDataResponseDto {

    private String firstName;
    private String lastName;
    private String email;


}
