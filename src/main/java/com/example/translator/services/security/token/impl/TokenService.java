package com.example.translator.services.security.token.impl;

import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.repository.TokenRepository;
import com.example.translator.services.security.token.AbstractTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TokenService extends AbstractTokenService {
    @Value("${token.email.validity}")
    private long emailValidity;

    @Value("${token.password.validity}")
    private long passwordValidity;

    @Value("${token.activation.validity}")
    private long activationValidity;

    private final TokenRepository tokenRepository;

    @Override
    protected TokenRepository getRepository() { return tokenRepository; }

    @Override
    protected long getExpirationSeconds(TokenTypeEnum type) {
        return switch (type) {
            case VERIFY_EMAIL    -> emailValidity;
            case PASSWORD_RESET  -> passwordValidity;
            case ACCOUNT_RECOVERY -> activationValidity;
        };
    }
}
