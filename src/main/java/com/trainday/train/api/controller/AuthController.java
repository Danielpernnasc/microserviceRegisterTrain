package com.trainday.train.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.train.api.DTO.request.LoginRequest;
import com.trainday.train.api.DTO.request.RegisterProfRequest;
import com.trainday.train.api.DTO.response.LoginResponse;
import com.trainday.train.application.LoginPhyEduProfService;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final LoginPhyEduProfService loginService;

    public AuthController(LoginPhyEduProfService loginService) {
        this.loginService = loginService;
    }

    @PostMapping("/register")
    public LoginResponse postMethodNafme(@RequestBody RegisterProfRequest request) {

        return loginService.createLogin(request);

    }

    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody LoginRequest request) {
        System.out.println("ENTROU NO LOGIN");

        String token = loginService.authenticate(request);
        return ResponseEntity.ok(token);
    }

}
