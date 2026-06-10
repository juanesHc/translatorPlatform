package com.example.translator.services.security.token.account;

import com.example.translator.dto.messaging.request.SendActivationRequestDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.exceptions.AccountRecoveryException;
import com.example.translator.repository.PersonRepository;
import com.example.translator.services.messaging.impl.MessagingService;
import com.example.translator.services.security.token.impl.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountRecoveryService {

    @Value("${app.base-url}")
    private String appBaseUrl;

    private final PersonRepository personRepository;
    private final TokenService tokenService;
    private final MessagingService messagingService;

    public void sendRecoveryEmail(String email) {
        PersonEntity person = personRepository.findByEmail(email);

        if (person == null) {
            throw new AccountRecoveryException("No existe una cuenta con ese correo");
        }
        if (person.isActivate()) {
            throw new AccountRecoveryException("La cuenta ya está activa");
        }

        TokenEntity token = tokenService.createToken(TokenTypeEnum.ACCOUNT_RECOVERY);
        token.setPerson(person);
        tokenService.saveToken(token);

        String recoveryUrl = appBaseUrl + "/recover?token=" + token.getToken();
        SendActivationRequestDto sendActivationRequestDto=new SendActivationRequestDto();
        sendActivationRequestDto.setUrl(recoveryUrl);
        sendActivationRequestDto.setPersonEntity(person);

        messagingService.sendAccountRecoveryMessage(sendActivationRequestDto);

        log.info("Email de recuperación enviado a {}", email);
    }


    @Transactional
    public void recoverAccount(String tokenValue) {
        try {
            TokenEntity token = tokenService.validateToken(tokenValue, TokenTypeEnum.ACCOUNT_RECOVERY);

            PersonEntity person = personRepository.findById(token.getPerson().getId())
                    .orElseThrow(() -> new AccountRecoveryException("Usuario no encontrado"));

            if (person.isActivate()) {
                tokenService.removeToken(token);
                throw new AccountRecoveryException("La cuenta ya estaba activa");
            }

            person.setActivate(true);
            personRepository.save(person);
            tokenService.removeToken(token);

            log.info("Cuenta recuperada exitosamente para {}", person.getEmail());

        } catch (AccountRecoveryException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error en recoverAccount tipo: {}", e.getClass().getName());
            log.error("Error en recoverAccount mensaje: {}", e.getMessage(), e);
            throw new AccountRecoveryException("Error al recuperar cuenta: " + e.getMessage());
        }
    }




}
