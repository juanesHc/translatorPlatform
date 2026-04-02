package com.example.translator.services.security.token.impl;

import com.example.translator.dto.token.TokenDto;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;
import com.example.translator.services.security.token.Token;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class TokenService implements Token {
    @Override
    public TokenEntity createToken(TokenTypeEnum tokenType) {
        return null;
    }

    @Override
    public void saveSecureToken(TokenEntity secureToken) {

    }

    @Override
    public void removeToken(TokenEntity secureToken) {

    }

    @Override
    public TokenEntity validateToken(TokenDto tokenDto) {
        return null;
    }
}
