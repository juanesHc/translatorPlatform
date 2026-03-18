package com.example.translator.services.ai;

import com.example.translator.db.entity.enums.LanguagesEnum;
import com.example.translator.dto.translation.response.TranslationResponseDto;
import com.example.translator.exceptions.TranslationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class AIService {

    private final WebClient groqWebClient;
    private final ObjectMapper objectMapper;

    @Value("${groq.api.url}")
    private String apiUrl;

    @Value("${groq.model}")
    private String model;

    public TranslationResponseDto translate(String text, LanguagesEnum targetLanguage) {
        String prompt = String.format(AIConstants.PROMPT_TEMPLATE, targetLanguage.name(), text);

        Map<String, Object> requestBody = Map.of(
                "model", model,
                "messages", List.of(
                        Map.of("role", "user", "content", prompt)
                ),
                "temperature", 0.3
        );

        String rawResponse = groqWebClient.post()
                .uri(apiUrl)
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(JsonNode.class)
                .map(json -> json
                        .path("choices")
                        .get(0)
                        .path("message")
                        .path("content")
                        .asText())
                .block();

        try {
            return objectMapper.readValue(rawResponse, TranslationResponseDto.class);
        } catch (TranslationException | JsonProcessingException e) {
            log.error("Error with groq in translation",e);
            throw new TranslationException("Error parsing Groq response: " + rawResponse);
        }
    }

}
