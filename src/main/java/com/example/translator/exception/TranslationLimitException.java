package com.example.translator.exception;

public class TranslationLimitException extends RuntimeException {
    public TranslationLimitException(String message) {
        super(message);
    }
}
