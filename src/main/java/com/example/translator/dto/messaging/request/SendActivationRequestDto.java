package com.example.translator.dto.messaging.request;

import com.example.translator.entity.PersonEntity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class SendActivationRequestDto {
    private PersonEntity personEntity;
    private String url;
}
