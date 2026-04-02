package com.example.translator.services.security.token;

import com.example.translator.dto.token.TokenDto;
import com.example.translator.entity.TokenEntity;
import com.example.translator.entity.enums.TokenTypeEnum;

public interface Token {

    TokenEntity createToken(TokenTypeEnum tokenType);

    void saveSecureToken(TokenEntity secureToken);

    void removeToken(TokenEntity secureToken);

    TokenEntity validateToken(TokenDto tokenDto);

}
