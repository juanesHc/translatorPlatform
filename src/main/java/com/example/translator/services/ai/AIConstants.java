package com.example.translator.services.ai;

import org.springframework.stereotype.Component;


@Component
class AIConstants {

    static final String PROMPT_TEMPLATE = """
            You are a translation assistant. Analyze the following text, detect its language, and translate it to %s.
            
            Respond ONLY with a valid JSON object in this exact format, no additional text, no markdown, no backticks:
            {
              "detectedLanguage": "SPANISH|ENGLISH|FRENCH|GERMAN|PORTUGUESE|ITALIAN",
              "translatedText": "the translated text here"
            }
            
            Text to translate:
            %s
            """;



}
