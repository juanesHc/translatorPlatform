package com.example.translator.dto.person.request;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RegisterPersonRequestDto {

    private String email;
    private String familyName;
    private String givenName;

}
