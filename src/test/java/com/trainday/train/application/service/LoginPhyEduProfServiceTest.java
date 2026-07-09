package com.trainday.train.application.service;


import com.trainday.train.api.DTO.request.LoginRequest;
import com.trainday.train.api.DTO.request.RegisterProfRequest;
import com.trainday.train.api.DTO.response.LoginResponse;
import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.LoginRepository;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;
import com.trainday.train.infra.service.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginPhyEduProfServiceTest {

    @Mock
    LoginRepository loginRepository;

    @Mock
    RepositoryPhyEdProf repositoryPhyEdProf;

    @Mock
    JwtService jwtService;

    @Mock
    AuthenticationManager authenticationManager;

    @Mock
    PasswordEncoder passwordEncoder;

    @InjectMocks
    LoginPhyEduProfService loginPhyEduProfService;


    @Test
    void shouldCreateLogin(){
        RegisterProfRequest request = new RegisterProfRequest(
                "CREF123456-G-SP",
               "professional@host.com",
                "password123",
                Role.PERSONAL_TRAINER
        );

        when(passwordEncoder.encode("password123")).thenReturn("encodedPassword");
        when(loginRepository.save(any(LoginPhyEdProf.class)))
                .thenAnswer(invocation -> {
                    LoginPhyEdProf savedLogin = invocation.getArgument(0);
                    savedLogin.setId("id-user");
                    return savedLogin;
                });

        LoginResponse service = loginPhyEduProfService.createLogin(request);

        assertNotNull(service);
        assertEquals("id-user", service.id());
        assertEquals("professional@host.com", service.email());

        verify(passwordEncoder).encode("password123");
        verify(loginRepository).save(any(LoginPhyEdProf.class));



    }

    @Test
    void shouldThrowWhenCrefOrEmailAlreadyRegistered() {
        RegisterProfRequest request = new RegisterProfRequest(
                "CREF123456-G-SP",
                "professional@host.com",
                "password123",
                Role.PERSONAL_TRAINER
        );

        when(loginRepository.existsByCref("CREF123456-G-SP")).thenReturn(true);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loginPhyEduProfService.createLogin(request)
        );

        assertEquals("Cref or Email already registered", exception.getMessage());
    }


    @Test
    void shouldAuthenticateLogin() {
        LoginPhyEdProf user = new LoginPhyEdProf();
        user.setId("id-user");
        user.setEmail("professional@host.com");
        user.setCref("CREF123456-G-SP");
        user.setPassword("123456");

        PhysicalEducationProfessional professional = new PhysicalEducationProfessional();
        professional.setId("use-123");
        professional.setCref("CREF123456-G-SP");

        when(loginRepository.findByEmail("professional@host.com"))
                .thenReturn(Optional.of(user));

        when(repositoryPhyEdProf.findByCref("CREF123456-G-SP"))
                .thenReturn(Optional.of(professional));
        user.setPassword("senhaCriptografada");

        when(passwordEncoder.matches(
                "123456",
                "senhaCriptografada"))
                        .thenReturn(true);

        when(jwtService.generateToken(
                anyString(),
                anyString(),
                anyString(),
                any(Role.class)))
                .thenReturn("token_fake");

        String token = loginPhyEduProfService.authenticate(new LoginRequest("professional@host.com", "123456"));

        assertEquals("token_fake", token);


    }

    @Test
    void shouldAuthenticateUsingCrefWhenEmailNotFound() {
        LoginPhyEdProf user = new LoginPhyEdProf();
        user.setId("id-user");
        user.setEmail("professional@host.com");
        user.setCref("CREF123456-G-SP");
        user.setPassword("senhaCriptografada");

        PhysicalEducationProfessional professional = new PhysicalEducationProfessional();
        professional.setId("use-123");
        professional.setCref("CREF123456-G-SP");
        professional.setRole(Role.PERSONAL_TRAINER);

        when(loginRepository.findByEmail("CREF123456-G-SP")).thenReturn(Optional.empty());
        when(loginRepository.findByCref("CREF123456-G-SP")).thenReturn(Optional.of(user));
        when(repositoryPhyEdProf.findByCref("CREF123456-G-SP")).thenReturn(Optional.of(professional));
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(true);
        when(jwtService.generateToken(anyString(), anyString(), anyString(), any(Role.class))).thenReturn("token_fake");

        String token = loginPhyEduProfService.authenticate(new LoginRequest("CREF123456-G-SP", "123456"));

        assertEquals("token_fake", token);
    }

    @Test
    void shouldThrowWhenCredentialsAreInvalid() {
        LoginPhyEdProf user = new LoginPhyEdProf();
        user.setEmail("professional@host.com");
        user.setPassword("senhaCriptografada");

        when(loginRepository.findByEmail("professional@host.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loginPhyEduProfService.authenticate(new LoginRequest("professional@host.com", "123456"))
        );

        assertEquals("Invalid credentials", exception.getMessage());
        assertEquals("Invalid credentials", exception.getMessage());
    }
    @Test
    void shouldThrowWhenAuthenticationManagerFails() {
        LoginPhyEdProf user = new LoginPhyEdProf();
        user.setId("id-user");
        user.setEmail("professional@host.com");
        user.setCref("CREF123456-G-SP");
        user.setPassword("senhaCriptografada");

        when(loginRepository.findByEmail("professional@host.com")).thenReturn(Optional.of(user));
        when(passwordEncoder.matches("123456", "senhaCriptografada")).thenReturn(true);
        when(authenticationManager.authenticate(any())).thenThrow(new RuntimeException("auth error"));

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> loginPhyEduProfService.authenticate(new LoginRequest("professional@host.com", "123456"))
        );

        assertEquals("Authentication failed", exception.getMessage());
        assertNotNull(exception.getCause());
        assertEquals("auth error", exception.getCause().getMessage());
    }





}
