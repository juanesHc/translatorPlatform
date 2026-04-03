package com.example.translator.services.messaging.impl;

import com.example.translator.dto.messaging.request.*;
import com.example.translator.dto.messaging.response.*;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TranslationEntity;
import com.example.translator.exceptions.PersonNotFoundException;
import com.example.translator.repository.PersonRepository;
import com.example.translator.repository.TranslationRepository;
import com.example.translator.services.messaging.Messaging;
import com.example.translator.services.translation.GenerateDocument;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class MessagingService implements Messaging {

    private final JavaMailSender mailSender;
    private final TranslationRepository translationRepository;
    private final PersonRepository personRepository;
    private final GenerateDocument generateDocument;
    private final ConstMail constMail;

    @Value("${spring.mail.username}")
    private String defaultSender;

    @Override
    public SendTranslationResponseDto sendTranslationByEmail(SendTranslationRequestDto requestDto) {

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
            helper.setText(constMail.buildHtmlBodyTranslation(requestDto.getSenderEmail(), requestDto.getMessage()), true);

            helper.addAttachment(
                    "translated_document.pdf",
                    new ByteArrayResource(pdfBytes),
                    "application/pdf"
            );

            mailSender.send(mimeMessage);
            return new SendTranslationResponseDto("Translated Document successfully sent");

        } catch (MessagingException e) {
            throw new RuntimeException("Error " + e.getMessage(), e);
        }
    }

    @Override
    public SendVerificationResponseDto sendVerificationEmail(SendVerificationRequestDto sendVerificationRequestDto) {
        return null;
    }

    @Override
    public SendActivationResponseDto sendAccountRecoveryMessage(SendActivationRequestDto sendActivationRequestDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(sendActivationRequestDto.getPersonEntity().getEmail());
            helper.setSubject("Recupera tu cuenta en TranslatorPlatform");

            String htmlBody = constMail.buildHtmlAccountRecovery(
                    sendActivationRequestDto.getPersonEntity().getGivenName(),
                    sendActivationRequestDto.getUrl()
            );
            helper.setText(htmlBody, true);
            mailSender.send(message);

            log.info("Email de recuperación enviado a {}",
                    sendActivationRequestDto.getPersonEntity().getEmail());

            return new SendActivationResponseDto("Email de recuperación enviado exitosamente");

        } catch (Exception e) {
            log.error("Error al enviar email de recuperación: {}", e.getMessage());
            return new SendActivationResponseDto("Error al enviar email: " + e.getMessage());
        }
    }

    @Override
    public SendWelcomeMessageResponseDto sendWelcomeEmail(SendWelcomeMessageRequestDto sendWelcomeMessageRequestDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(sendWelcomeMessageRequestDto.getPersonEmail());
            helper.setSubject("¡Bienvenido a TranslatorPlatform!");

            String htmlBody = constMail.buildHtmlWelcome(sendWelcomeMessageRequestDto.getPersonName());
            helper.setText(htmlBody, true);

            mailSender.send(message);

            return new SendWelcomeMessageResponseDto("Correo de bienvenida enviado exitosamente");
        } catch (Exception e) {
            log.error("It run into a issue",e);
            return new SendWelcomeMessageResponseDto("Error al enviar el correo " + e.getMessage());
        }
    }

    @Override
    public SendBlockMessageResponseDto sendBlockEmail(SendBlockMessageRequestDto sendBlockMessageRequestDto) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setTo(sendBlockMessageRequestDto.getPersonEmail());
            helper.setSubject("Notificación de cuenta: Acceso Restringido");

            String htmlBody = constMail.buildHtmlBlock(sendBlockMessageRequestDto.getPersonName());
            helper.setText(htmlBody, true);

            mailSender.send(message);

            return new SendBlockMessageResponseDto("Notificación de bloqueo enviada");
        } catch (Exception e) {
            return new SendBlockMessageResponseDto("Error al notificar bloqueo: " + e.getMessage());
        }
    }

}
