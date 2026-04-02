package com.example.translator.dto.messaging.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SendWelcomeMessageRequestDto {
    private String personName;
    private String personEmail;
    private String message;
}
