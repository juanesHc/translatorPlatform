package com.example.translator.services.security.token;

import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.exceptions.InvalidTokenTypeException;
import com.example.translator.exceptions.TokenExpiredException;
import com.example.translator.exceptions.TokenNotFoundException;
import com.example.translator.repository.TokenRepository;
import org.apache.commons.codec.binary.Base64;
import org.springframework.security.crypto.keygen.BytesKeyGenerator;
import org.springframework.security.crypto.keygen.KeyGenerators;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

public abstract class AbstractTokenService {

    protected abstract TokenRepository getRepository();
    protected abstract long getExpirationSeconds(TokenTypeEnum type);

    private static final BytesKeyGenerator TOKEN_GENERATOR =
            KeyGenerators.secureRandom(12);

    public TokenEntity createToken(TokenTypeEnum tokenType) {
        TokenEntity token = new TokenEntity();
        token.setToken(generateTokenValue());
        token.setType(tokenType);
        token.setExpiredAt(LocalDateTime.now().plusSeconds(
                getExpirationSeconds(tokenType)
        ));
        return token;
    }

    public void saveToken(TokenEntity token) {
        getRepository().save(token);
    }

    public void removeToken(TokenEntity token) {
        getRepository().delete(token);
    }

    public TokenEntity validateToken(String tokenValue, TokenTypeEnum tokenType) {
        TokenEntity token = getRepository().findByToken(tokenValue);
        if (token == null) throw new TokenNotFoundException("Token not found");
        if (token.getExpiredAt().isBefore(LocalDateTime.now())) throw new TokenExpiredException("Token expired");
        if (token.getType() != tokenType) throw new InvalidTokenTypeException("Token type mismatch");
        return token;
    }

    private String generateTokenValue() {
        return new String(
                Base64.encodeBase64URLSafe(TOKEN_GENERATOR.generateKey()),
                StandardCharsets.UTF_8
        );
    }
}
