package com.example.translator.dto.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@AllArgsConstructor
public class ExceptionDto {
    private String message;
    private String code;
    private LocalDateTime timestamp = LocalDateTime.now();

    public ExceptionDto(final String message,final String code) {
        this.message = message;
        this.code = code;
    }
}
