package com.example.translator.controller.messaging;

import com.example.translator.dto.messaging.request.SendTranslationRequestDto;
import com.example.translator.dto.messaging.response.SendTranslationResponseDto;
import com.example.translator.services.messaging.impl.MessagingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/messaging")
public class MessagingController {

    private final MessagingService messagingService;

    @PostMapping("/translation")
    public ResponseEntity<SendTranslationResponseDto> sendEmail(@RequestBody SendTranslationRequestDto requestDto) {
        return ResponseEntity.ok(messagingService.sendTranslationByEmail(requestDto));
    }

}
