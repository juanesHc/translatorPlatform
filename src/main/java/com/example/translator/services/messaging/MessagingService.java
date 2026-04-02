package com.example.translator.services.messaging;

import com.example.translator.dto.messaging.request.SendEmailRequestDto;
import com.example.translator.dto.messaging.response.SendEmailResponseDto;
import com.example.translator.entity.TranslationEntity;
import com.example.translator.repository.TranslationRepository;
import com.example.translator.services.translation.GenerateDocument;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MessagingService {

    private final JavaMailSender mailSender;
    private final TranslationRepository translationRepository;
    private final GenerateDocument generateDocument;

    @Value("${spring.mail.username}")
    private String defaultSender;

    public SendEmailResponseDto sendTranslationByEmail(SendEmailRequestDto requestDto) {

        TranslationEntity translation = translationRepository
                .findById(UUID.fromString(requestDto.getTranslationId()))
                .orElseThrow(() -> new RuntimeException("Translation not found"));

        byte[] pdfBytes = generateDocument.generatePdfBytes(translation.getTranslatedText());

        try {
            MimeMessage mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");

            helper.setFrom(defaultSender);
            helper.setTo(requestDto.getRecipientEmail());
            helper.setSubject(requestDto.getSubject());
            helper.setText(buildHtmlBody(requestDto.getSenderEmail(), requestDto.getMessage()), true);

            helper.addAttachment(
                    "translated_document.pdf",
                    new ByteArrayResource(pdfBytes),
                    "application/pdf"
            );

            mailSender.send(mimeMessage);
            return new SendEmailResponseDto("Translated Document successfully sent");

        } catch (MessagingException e) {
            throw new RuntimeException("Error " + e.getMessage(), e);
        }
    }

    private String buildHtmlBody(String senderEmail, String message) {
        String messageSection = (message != null && !message.isBlank())
                ? "<p style='color:#444;font-size:15px;'>" + message + "</p>"
                : "";

        return """
                <div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto;padding:32px;background:#f5f5f5;'>
                  <div style='background:#1a2744;padding:24px;border-radius:12px 12px 0 0;text-align:center;'>
                    <h1 style='color:#f5c542;margin:0;font-size:22px;'>TranslatorPlatform</h1>
                  </div>
                  <div style='background:#ffffff;padding:32px;border-radius:0 0 12px 12px;'>
                    <p style='color:#1a2744;font-size:16px;font-weight:bold;'>Tienes un documento traducido</p>
                    <p style='color:#666;font-size:14px;'>
                      <strong>%s</strong> te ha enviado una traducción desde TranslatorPlatform.
                    </p>
                    %s
                    <div style='margin-top:24px;padding:16px;background:#f9f9f9;border-radius:8px;border-left:4px solid #f5c542;'>
                      <p style='margin:0;color:#666;font-size:13px;'>
                        El documento traducido se encuentra adjunto en este correo en formato PDF.
                      </p>
                    </div>
                    <p style='margin-top:32px;color:#aaa;font-size:12px;text-align:center;'>
                      Enviado desde TranslatorPlatform
                    </p>
                  </div>
                </div>
                """.formatted(senderEmail, messageSection);
    }
}
