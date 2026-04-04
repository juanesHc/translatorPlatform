package com.example.translator.exceptions;

public class ForgotPasswordException extends RuntimeException {
    public ForgotPasswordException(String message) {
        super(message);
    }
}
