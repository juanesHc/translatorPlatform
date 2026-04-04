package com.example.translator.services.messaging.impl;

import lombok.Getter;
import org.springframework.stereotype.Component;

@Component
public class ConstMail {

    public String buildHtmlWelcome(String name) {
        String content = "<p style='color:#1a2744;font-size:16px;font-weight:bold;'>¡Bienvenido/a, " + name + "!</p>" +
                "<p style='color:#666;font-size:14px;'>Nos alegra tenerte en TranslatorPlatform. Tu cuenta ha sido creada exitosamente y ya puedes empezar a traducir tus documentos.</p>";
        return buildBaseTemplate("Bienvenido a la Plataforma", content);
    }

    public String buildHtmlBlock(String name) {
        String content = "<p style='color:#d93025;font-size:16px;font-weight:bold;'>Aviso de Seguridad</p>" +
                "<p style='color:#666;font-size:14px;'>Hola " + name + ", te informamos que tu cuenta en TranslatorPlatform ha sido <strong>bloqueada</strong> temporalmente debido a políticas de seguridad.</p>";
        return buildBaseTemplate("Cuenta Bloqueada", content);
    }

    public String buildHtmlBodyTranslation(String senderEmail, String message) {
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

    public String buildHtmlAccountRecovery(String name, String recoveryUrl) {
        String content = "<p style='color:#1a2744;font-size:16px;font-weight:bold;'>Recupera tu cuenta, " + name + "</p>" +
                "<p style='color:#666;font-size:14px;'>Recibimos una solicitud para reactivar tu cuenta en TranslatorPlatform. " +
                "Si fuiste tú, haz clic en el botón para recuperarla.</p>" +
                "<div style='text-align:center;margin:32px 0;'>" +
                "<a href='" + recoveryUrl + "' " +
                "style='background:#f5c542;color:#1a2744;padding:14px 32px;border-radius:8px;" +
                "text-decoration:none;font-weight:bold;font-size:15px;'>Recuperar mi cuenta</a>" +
                "</div>" +
                "<p style='color:#aaa;font-size:12px;'>Si no solicitaste esto, ignora este correo. " +
                "Tu cuenta permanecerá desactivada.</p>";
        return buildBaseTemplate("Recupera tu cuenta", content);
    }

    public String buildHtmlVerifyEmail(String name, String verifyUrl) {
        String content = "<p style='color:#1a2744;font-size:16px;font-weight:bold;'>Verifica tu correo, " + name + "</p>" +
                "<p style='color:#666;font-size:14px;'>Gracias por registrarte en TranslatorPlatform. " +
                "Para activar tu cuenta haz clic en el botón.</p>" +
                "<div style='text-align:center;margin:32px 0;'>" +
                "<a href='" + verifyUrl + "' " +
                "style='background:#f5c542;color:#1a2744;padding:14px 32px;border-radius:8px;" +
                "text-decoration:none;font-weight:bold;font-size:15px;'>Verificar mi correo</a>" +
                "</div>" +
                "<p style='color:#aaa;font-size:12px;'>Si no creaste esta cuenta, ignora este correo.</p>";
        return buildBaseTemplate("Verifica tu correo", content);
    }

    public String buildHtmlForgotPassword(String name, String resetUrl) {
        String content = "<p style='color:#1a2744;font-size:16px;font-weight:bold;'>Restablece tu contraseña, " + name + "</p>" +
                "<p style='color:#666;font-size:14px;'>Recibimos una solicitud para restablecer la contraseña de tu cuenta. " +
                "Si fuiste tú, haz clic en el botón.</p>" +
                "<div style='text-align:center;margin:32px 0;'>" +
                "<a href='" + resetUrl + "' " +
                "style='background:#f5c542;color:#1a2744;padding:14px 32px;border-radius:8px;" +
                "text-decoration:none;font-weight:bold;font-size:15px;'>Restablecer contraseña</a>" +
                "</div>" +
                "<p style='color:#aaa;font-size:12px;'>Si no solicitaste esto, ignora este correo. " +
                "Tu contraseña no será cambiada.</p>";
        return buildBaseTemplate("Restablece tu contraseña", content);
    }

    private String buildBaseTemplate(String title, String content) {
        return """
                <div style='font-family:Arial,sans-serif;max-width:600px;margin:0 auto;padding:32px;background:#f5f5f5;'>
                  <div style='background:#1a2744;padding:24px;border-radius:12px 12px 0 0;text-align:center;'>
                    <h1 style='color:#f5c542;margin:0;font-size:22px;'>TranslatorPlatform</h1>
                  </div>
                  <div style='background:#ffffff;padding:32px;border-radius:0 0 12px 12px;'>
                    %s
                    <p style='margin-top:32px;color:#aaa;font-size:12px;text-align:center;'>
                      Enviado desde TranslatorPlatform
                    </p>
                  </div>
                </div>
                """.formatted(content);
    }
}
