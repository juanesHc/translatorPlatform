package com.example.translator.services.security.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Service
public class JwtService {

    @Value("${jwt.secret-key}")
    private String jwtSecret;

    @Value("${jwt.expiration}")
    private long jwtExpiration;

    private SecretKey getSigningKey() {
        return Keys.hmacShaKeyFor(jwtSecret.getBytes(StandardCharsets.UTF_8));
    }

    public String generateToken(UserDetails userDetails,
                            UUID personId,
                            boolean block ,
                            boolean activate,
                            String email,
                            String givenName,
                            String role,
                                boolean verify){
        Map<String, Object> claims=new HashMap<>();
        claims.put("personId",personId);
        claims.put("block",block);
        claims.put("activate",activate);
        claims.put("email",email);
        claims.put("givenName",givenName);
        claims.put("role",role);
        claims.put("verify",verify);
        return buildToken(claims,userDetails.getUsername());
    }

    private String buildToken(Map<String,Object> claims, String subject){
        return Jwts.builder().
                claims(claims).
                subject(subject).
                issuedAt(new Date()).
                expiration(expirationTime()).
                signWith(getSigningKey()).
                compact();
    }

    private Date expirationTime(){
        return new Date(System.currentTimeMillis()+jwtExpiration);
    }

    public String extractUserName(String token){
        return extractClaim(token, Claims::getSubject);
    }

    public <T> T extractClaim(String token, Function<Claims,T> resolver){
        Claims claims = extractAllClaims(token);
        return resolver.apply(claims);
    }

    public boolean isTokenValid(String token, UserDetails userDetails){
        String username = extractUserName(token);
        return username.equals(userDetails.getUsername())
                && !isTokenExpired(token);
    }

    private boolean isTokenExpired(String token){
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }

    private Claims extractAllClaims(String token){
        return Jwts.parser()
                .verifyWith(getSigningKey())
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }


}
