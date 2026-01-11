package com.example.spring.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import java.security.Key;
import java.util.Date;

public class JwtUtilitario {

    private static final String SECRET =
            "minha-chave-super-secreta-min-32-chars!!!";

    private static final long EXPIRATION_MS = 1000 * 60 * 60;

    private static final Key KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

//    Converte a String SECRET numa Key criptográfica
    public static String generateToken(String email, String role) {

        Date now = new Date();
        Date expiry = new Date(now.getTime() + EXPIRATION_MS);

        return Jwts.builder() //construi token
                .setSubject(email)//identificca user
                .claim("role", role)
                .setIssuedAt(now)//data craiacao
                .setExpiration(expiry)// data expiracao
                .signWith(KEY, SignatureAlgorithm.HS256)
                .compact();//gera a string final
    }

    public static Claims validateToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(KEY)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }
}

