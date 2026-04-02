package com.example.translator.controller.messaging;

import com.example.translator.dto.messaging.request.SendEmailRequestDto;
import com.example.translator.dto.messaging.response.SendEmailResponseDto;
import com.example.translator.services.messaging.MessagingService;
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
    public ResponseEntity<SendEmailResponseDto> sendEmail(@RequestBody SendEmailRequestDto requestDto) {
        return ResponseEntity.ok(messagingService.sendTranslationByEmail(requestDto));
    }

}
