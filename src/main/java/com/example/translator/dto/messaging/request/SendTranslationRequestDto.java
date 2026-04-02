package com.example.translator.dto.messaging.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SendTranslationRequestDto {
    private String translationId;
    private String senderEmail;
    private String recipientEmail;
    private String subject;
    private String message;


}
