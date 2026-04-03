package com.example.translator.controller.exception;

import com.example.translator.dto.exception.ExceptionDto;
import com.example.translator.exceptions.*;
import jakarta.transaction.TransactionalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // Archivos
    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionDto> handleFileSize(MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ExceptionDto("El archivo es demasiado grande", "FILE_TOO_LARGE"));
    }

    // Login — ✅ ahora pasa el mensaje real (ACCOUNT_DELETED, ACCOUNT_BLOCKED, etc.)
    @ExceptionHandler(LoginException.class)
    public ResponseEntity<ExceptionDto> handleLogin(LoginException ex) {
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)  // ✅ 401 es más correcto que 500
                .body(new ExceptionDto(ex.getMessage(), "LOGIN_FAILED"));
    }

    // Personas
    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFindingPerson(PersonNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(ex.getMessage(), "PERSON_NOT_FOUND"));
    }

    // Documentos
    @ExceptionHandler(DocumentProcessingException.class)
    public ResponseEntity<ExceptionDto> handleDocumentProcessing(DocumentProcessingException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(new ExceptionDto(ex.getMessage(), "DOCUMENT_PROCESSING_ERROR"));
    }

    @ExceptionHandler(DocumentNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleDocumentNotFound(DocumentNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(ex.getMessage(), "DOCUMENT_NOT_FOUND"));
    }

    @ExceptionHandler(UpdateDocumentException.class)
    public ResponseEntity<ExceptionDto> handleUpdateDocument(UpdateDocumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(ex.getMessage(), "UPDATE_DOCUMENT_FAILED"));
    }

    // Traducciones
    @ExceptionHandler(TranslationException.class)
    public ResponseEntity<ExceptionDto> handleTranslation(TranslationException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(ex.getMessage(), "TRANSLATION_FAILED"));
    }

    @ExceptionHandler(TransactionalException.class)
    public ResponseEntity<ExceptionDto> handleTransactional(TransactionalException ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto(ex.getMessage(), "TRANSACTIONAL_ERROR"));
    }

    // Roles
    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFindingRole(RoleNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(ex.getMessage(), "ROLE_NOT_FOUND"));
    }

    // Registro
    @ExceptionHandler(RegisterPersonGoogleException.class)
    public ResponseEntity<ExceptionDto> handleRegisterPerson(RegisterPersonGoogleException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(ex.getMessage(), "REGISTER_PERSON_FAILED"));
    }

    // Tokens
    @ExceptionHandler(TokenNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleTokenNotFound(TokenNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ExceptionDto(ex.getMessage(), "TOKEN_NOT_FOUND"));
    }

    @ExceptionHandler(TokenExpiredException.class)
    public ResponseEntity<ExceptionDto> handleTokenExpired(TokenExpiredException ex) {
        return ResponseEntity
                .status(HttpStatus.GONE)  // 410 = recurso expirado
                .body(new ExceptionDto(ex.getMessage(), "TOKEN_EXPIRED"));
    }

    @ExceptionHandler(InvalidTokenTypeException.class)
    public ResponseEntity<ExceptionDto> handleInvalidTokenType(InvalidTokenTypeException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(ex.getMessage(), "INVALID_TOKEN_TYPE"));
    }

    // Recuperación de cuenta
    @ExceptionHandler(AccountRecoveryException.class)
    public ResponseEntity<ExceptionDto> handleAccountRecovery(AccountRecoveryException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionDto(ex.getMessage(), "ACCOUNT_RECOVERY_FAILED"));
    }

    // Genérico — siempre al final
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("Error inesperado", "INTERNAL_ERROR"));
    }

}