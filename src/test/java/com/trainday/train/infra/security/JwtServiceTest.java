package com.trainday.train.infra.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp(){
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", "fake-secret-key-for-testing-trainday-2026!!");
        ReflectionTestUtils.setField(jwtService, "experation", 3600000L);
    }

    @Test
    void shouldReturnedKey(){
        Key key = jwtService.testgetKey();
        assertNotNull(key);
    }


    @Test
    void shouldGenerateToken(){
        String token = jwtService.generateToken("usuary-123");

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldExtractEmail(){
        String token = jwtService.generateToken("athlete@host.com.br");
        String email = jwtService.extractEmail(token);
        assertEquals("athlete@host.com.br", email);
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid(){
        String token = jwtService.generateToken("athlete@host.com.br");
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void shouldReturnFalseWhenTokenIsValid(){
        assertFalse(jwtService.isTokenValid("token-invalido"));
    }

    @Test
    void shouldExtractUsername(){
        String token = jwtService.generateToken("athlete@host.com.br");
        String username = jwtService.extractUsername(token);
        assertEquals("athlete@host.com.br", username);
    }
}
