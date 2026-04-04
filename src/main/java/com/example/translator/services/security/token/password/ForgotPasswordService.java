package com.example.translator.services.security.token.password;

import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.AuthEnum;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.exceptions.ForgotPasswordException;
import com.example.translator.repository.PersonRepository;
import com.example.translator.services.security.token.impl.TokenService;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.example.translator.services.messaging.impl.ConstMail;

@Slf4j
@Service
@RequiredArgsConstructor
public class ForgotPasswordService {

    @Value("${app.base-url}")
    private String appBaseUrl;

    private final PersonRepository personRepository;
    private final TokenService tokenService;
    private final JavaMailSender mailSender;
    private final ConstMail constMail;
    private final PasswordEncoder passwordEncoder;

    public void sendForgotPasswordEmail(String email) {
        PersonEntity person = personRepository.findByEmail(email);

        if (person == null) {
            throw new ForgotPasswordException("No existe una cuenta con ese correo");
        }

        if (person.getAuthEnum() != AuthEnum.CLASSIC) {
            throw new ForgotPasswordException("Esta cuenta usa Google para autenticarse. No puedes restablecer la contraseña.");
        }

        TokenEntity token = tokenService.createToken(TokenTypeEnum.PASSWORD_RESET);
        token.setPerson(person);
        tokenService.saveToken(token);

        String resetUrl = appBaseUrl + "/reset-password?token=" + token.getToken();

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setTo(person.getEmail());
            helper.setSubject("Restablece tu contraseña en TranslatorPlatform");
            helper.setText(constMail.buildHtmlForgotPassword(person.getGivenName(), resetUrl), true);
            mailSender.send(message);
            log.info("Email de restablecimiento enviado a {}", email);
        } catch (Exception e) {
            log.error("Error enviando email de restablecimiento: {}", e.getMessage());
            throw new ForgotPasswordException("Error al enviar email: " + e.getMessage());
        }
    }

    @Transactional
    public void resetPassword(String tokenValue, String newPassword, String confirmPassword) {
        if (!newPassword.equals(confirmPassword)) {
            throw new ForgotPasswordException("Las contraseñas no coinciden");
        }

        if (newPassword.length() < 8) {
            throw new ForgotPasswordException("La contraseña debe tener al menos 8 caracteres");
        }

        try {
            TokenEntity token = tokenService.validateToken(tokenValue, TokenTypeEnum.PASSWORD_RESET);
            PersonEntity person = token.getPerson();

            person.setPassword(passwordEncoder.encode(newPassword));
            personRepository.save(person);
            tokenService.removeToken(token);

            log.info("Contraseña restablecida para {}", person.getEmail());
        } catch (ForgotPasswordException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error restableciendo contraseña: {}", e.getClass().getName());
            throw new ForgotPasswordException("Error al restablecer contraseña: " + e.getMessage());
        }
    }
}
