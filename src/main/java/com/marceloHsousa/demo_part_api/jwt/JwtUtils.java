package com.marceloHsousa.demo_part_api.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

@Slf4j
public class JwtUtils {

    public static final String JWT_BEARER = "bearer";

    public static final String JWT_AUTHORIZATION = "authorization";

    public static final String SECRET_KEY = "0123456789-0123456789-0123456789";

    public static final long  EXPIRE_DAYS = 0;

    public static final long  EXPIRE_HOURS = 0;

    public static final long  EXPIRE_MINUTES = 2;


    private JwtUtils(){
    }

    private static javax.crypto.SecretKey generateKey(){
        return Keys.hmacShaKeyFor(SECRET_KEY.getBytes(StandardCharsets.UTF_8));
    }

    private static Date toExpireDate(Date start){

        LocalDateTime dateTime = start.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime end = dateTime.plusDays(EXPIRE_DAYS).plusHours(EXPIRE_HOURS).plusMinutes(EXPIRE_MINUTES);

        return Date.from(end.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static JwtToken createToken(String username, String role){

        Date issueAt = new Date();
        Date limit = toExpireDate(issueAt);

        String token = Jwts.builder()
                .header().add("typ", "JWT")
                .and()
                .subject(username)
                .issuedAt(issueAt)
                .expiration(limit)
                .signWith(generateKey())
                .claim("role", role)
                .compact();

        return new JwtToken(token);

    }

    private static Claims getClaimsFromToken(String token){

        try {

            return Jwts.parser()
                    .verifyWith(generateKey())
                    .build()
                    .parseSignedClaims(refactorToken(token)).getPayload();

        }catch (JwtException e){
            log.error(String.format("Invalid token %s", e.getMessage()));
        }
        return null;
    }

    private static String refactorToken(String token){

        if(token.contains(JWT_BEARER)){
            return token.substring(JWT_BEARER.length());
        }
        return token;
    }

    public static String getUsernameFromToken(String token){
        return getClaimsFromToken(token).getSubject();
    }

    public boolean isTokenValid(String token){

        try {

             Jwts.parser()
                     .verifyWith(generateKey())
                     .build()
                     .parseSignedClaims(refactorToken(token));

             return true;

        }catch (JwtException e){
            log.error(String.format("Invalid token", e.getMessage()));
        }
        return false;
    }
}
