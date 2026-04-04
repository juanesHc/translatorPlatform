package com.example.translator.services.security.token.email;

import com.example.translator.dto.messaging.request.SendActivationRequestDto;
import com.example.translator.dto.messaging.request.SendVerificationRequestDto;
import com.example.translator.dto.messaging.response.SendVerificationResponseDto;
import com.example.translator.entity.PersonEntity;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.exceptions.AccountRecoveryException;
import com.example.translator.exceptions.VerifyAccountException;
import com.example.translator.repository.PersonRepository;
import com.example.translator.services.messaging.impl.MessagingService;
import com.example.translator.services.security.token.impl.TokenService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class VerifyEmailService {

    private final PersonRepository personRepository;
    private final TokenService tokenService;
    private final MessagingService messagingService;

    @Transactional
    public void verifyEmail(String tokenValue) {
        try {
            TokenEntity token = tokenService.validateToken(tokenValue, TokenTypeEnum.VERIFY_EMAIL);

            PersonEntity person = personRepository.findById(token.getPerson().getId())
                    .orElseThrow(() -> new AccountRecoveryException("User not found"));

            if (person.isVerify()) {
                tokenService.removeToken(token);
                throw new VerifyAccountException("account is already verify");
            }

            person.setVerify(true);
            personRepository.save(person);
            tokenService.removeToken(token);

            log.info("Cuenta verificada exitosamente para {}", person.getEmail());

        } catch (VerifyAccountException e) {
            throw e;
        } catch (Exception e) {
            log.error("Error en verifyAccount tipo: {}", e.getClass().getName());
            log.error("Error en verifyAccount mensaje: {}", e.getMessage(), e);
            throw new AccountRecoveryException("Error al verificar cuenta: " + e.getMessage());
        }
    }





    public SendVerificationResponseDto reVerifyEmail(String email){
        return messagingService.sendVerificationEmail(new SendVerificationRequestDto(email));
    }


}
