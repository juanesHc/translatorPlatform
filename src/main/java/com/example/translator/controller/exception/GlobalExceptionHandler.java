package com.example.translator.controller.exception;

import com.example.translator.dto.exception.ExceptionDto;
import com.example.translator.exceptions.DocumentProcessingException;
import com.example.translator.exceptions.PersonNotFoundException;
import com.example.translator.exceptions.RegisterPersonGoogleException;
import com.example.translator.exceptions.RoleNotFoundException;
import jakarta.transaction.TransactionalException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<ExceptionDto> handleFileSize(
            MaxUploadSizeExceededException ex) {
        return ResponseEntity
                .status(HttpStatus.PAYLOAD_TOO_LARGE)
                .body(new ExceptionDto("the file is too large", "FILE_TOO_LARGE"));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionDto> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("Unexpected Error", "INTERNAL_ERROR"));
    }

    @ExceptionHandler(DocumentProcessingException.class)
    public ResponseEntity<ExceptionDto> handleDocumentProcessing(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("It was impossible to process the document", "DOCUMENT_PROCESSING_ERROR"));
    }

    @ExceptionHandler(PersonNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFindingPerson(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("It was impossible to found the according person", "PERSON_NOT_FOUND"));
    }

    @ExceptionHandler(RegisterPersonGoogleException.class)
    public ResponseEntity<ExceptionDto> handleRegisterPerson(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("It was impossible to register person", "REGISTER_PERSON_FAILED"));
    }

    @ExceptionHandler(RoleNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleFindingRole(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("It was impossible to found the according role", "ROLE_NOT_FOUND"));
    }

    @ExceptionHandler(TransactionalException.class)
    public ResponseEntity<ExceptionDto> handleTranslation(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ExceptionDto("It was impossible to translate the document", "TRANSLATION_FAILED"));
    }
}