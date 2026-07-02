package com.trainday.train.infra.security;

import static org.junit.jupiter.api.Assertions.*;

import java.security.Key;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.test.util.ReflectionTestUtils;

import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.infra.service.JwtService;

public class JwtServiceTest {

    @InjectMocks
    private JwtService jwtService;

    @BeforeEach
    void setUp() {
        jwtService = new JwtService();

        ReflectionTestUtils.setField(jwtService, "secret", "fake-secret-key-for-testing-trainday-2026!!");
        ReflectionTestUtils.setField(jwtService, "expiration", 3600000L);
    }

    @Test
    void shouldReturnedKey() {
        Key key = jwtService.testgetKey();
        assertNotNull(key);
    }

    @Test
    void shouldGenerateToken() {
        String token = jwtService.generateToken(
                "athlete@host.com.br", "John Doe", "athlete", Role.ATHLETE);

        assertNotNull(token);
        assertFalse(token.isBlank());
    }

    @Test
    void shouldReturnTrueWhenTokenIsValid() {
        String token = jwtService.generateToken("athlete@host.com.br", "John Doe", "athlete", Role.ATHLETE);
        assertTrue(jwtService.isTokenValid(token));
    }

    @Test
    void shouldReturnFalseWhenTokenIsInvalid() {
        assertFalse(jwtService.isTokenValid("token-invalido"));
    }

    @Test
    void shouldExtractUsername() {
        String token = jwtService.generateToken("athlete@host.com.br", "John Doe", "athlete", Role.ATHLETE);
        String username = jwtService.extractUserName(token);
        assertEquals("athlete@host.com.br", username);
    }
}
