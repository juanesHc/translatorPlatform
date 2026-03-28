package com.example.translator.dto.person.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterClassicPersonRequestDto {
    private String password;
    private String email;
    private String givenName;
    private String familyName;
    private String confirmPassword;
}
