package com.example.translator.exceptions;

public class TranslationLimitException extends RuntimeException {
    public TranslationLimitException(String message) {
        super(message);
    }
}
