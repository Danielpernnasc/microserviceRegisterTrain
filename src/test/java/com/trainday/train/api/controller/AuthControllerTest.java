package com.trainday.train.api.controller;

import com.trainday.train.api.DTO.request.LoginRequest;
import com.trainday.train.api.DTO.request.RegisterProfRequest;
import com.trainday.train.api.DTO.response.LoginResponse;
import com.trainday.train.application.service.LoginPhyEduProfService;
import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.enums.Role;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    LoginPhyEdProf loginPhyEdProf;

    @Mock
    LoginPhyEduProfService service;

    @InjectMocks
    AuthController authController;

    @Test
    void shouldRegister(){
        RegisterProfRequest request = new RegisterProfRequest(
                "CREF123456-G-SP",
                "professional@host.com",
                "123456",
                Role.PERSONAL_TRAINER
        );

        LoginResponse response = new LoginResponse(
                "user1",
                "professional@host.com",
                "CREF123456-G-SP"
        );

        when(service.createLogin(request)).thenReturn(response);

        LoginResponse created = authController.register(request);

        assertNotNull(created);
        assertEquals("user1", created.id());
        assertEquals("professional@host.com", created.email());

    }

    @Test
    void shouldLogin(){

        LoginRequest loginreq = new LoginRequest(
                "professional@host.com",
                "123456"
        );

        when(service.authenticate(loginreq)).thenReturn("meu-token");

        ResponseEntity<?> response = authController.login(loginreq);

        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());


        assertNotNull(response.getBody());

        verify(service).authenticate(loginreq);

    }
}
