package com.trainday.train.infra.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

@Service
public class JwtService{

    @PostConstruct
    public void init() {
        System.out.println("JWT SECRET 8081 = [" + secret + "]");
    }

    @Value("${jwt.secret}")
    private String secret;

    @Value("${jwt.expiration}")
    private Long experation;

    
    private Key getKey(){
        return Keys.hmacShaKeyFor(secret.getBytes());
    }

    
    public Key testgetKey(){
        return getKey();
    }

    public String generateToken(String id){
        return Jwts.builder()
                .setSubject(id)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + experation))
                .signWith(getKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    public String extractEmail(String token){
        return Jwts.parserBuilder()
                .setSigningKey(getKey())
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    public boolean isTokenValid(String token){
        try{
            Jwts.parserBuilder().setSigningKey(getKey()).build().parseClaimsJws(token);
            return true;
        }catch (JwtException e){
            return false;
        }
    }

    public String extractUsername(String token) {
          return Jwts.parserBuilder()
                   .setSigningKey(getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody()
                    .getSubject();
    }

    
    

}
