package com.example.translator.controller.translation;
import com.example.translator.dto.translation.request.TranslationRequestDto;
import com.example.translator.dto.translation.response.RegisterTranslationResponseDto;
import com.example.translator.dto.translation.response.RetrieveTranslationResponseDto;
import com.example.translator.entity.TranslationEntity;
import com.example.translator.services.translation.RegisterTranslationService;
import com.example.translator.services.translation.RetrieveTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController {

    private final RegisterTranslationService registerTranslationService;
    private final RetrieveTranslationService retrieveTranslationService;


    @PostMapping("/{documentId}")
    public ResponseEntity<RegisterTranslationResponseDto> translate(
            @PathVariable String documentId,
            @RequestBody TranslationRequestDto requestDto) {

        return ResponseEntity.ok(registerTranslationService.translate(documentId, requestDto));
    }

    @GetMapping("/document/{documentId}")
    public ResponseEntity<List<RetrieveTranslationResponseDto>> getTranslationsByDocument(
            @PathVariable String documentId) {
        return ResponseEntity.ok(retrieveTranslationService.getTranslationsByDocument(documentId));
    }

    @GetMapping("/{translationId}/pdf")
    public ResponseEntity<byte[]> downloadTranslatedPdf(@PathVariable String translationId) {
        byte[] pdfBytes = retrieveTranslationService.getPdfByTranslationId(translationId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"translated_document.pdf\"")
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfBytes);
    }

}
