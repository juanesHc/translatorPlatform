package com.example.translator.controller.translation;
import com.example.translator.dto.translation.request.TranslationRequestDto;
import com.example.translator.dto.translation.response.RegisterTranslationResponseDto;
import com.example.translator.services.translation.RegisterTranslationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/translation")
@RequiredArgsConstructor
public class TranslationController {

    private final RegisterTranslationService registerTranslationService;

    @PostMapping("/{documentId}")
    public ResponseEntity<RegisterTranslationResponseDto> translate(
            @PathVariable String documentId,
            @RequestBody TranslationRequestDto requestDto) {

        return ResponseEntity.ok(registerTranslationService.translate(documentId, requestDto));
    }
}
