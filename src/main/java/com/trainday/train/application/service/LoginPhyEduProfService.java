package com.trainday.train.application.service;

import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.trainday.train.api.DTO.request.LoginRequest;
import com.trainday.train.api.DTO.request.RegisterProfRequest;
import com.trainday.train.api.DTO.response.LoginResponse;
import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.LoginRepository;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;
import com.trainday.train.infra.service.JwtService;

@Service
public class LoginPhyEduProfService {

    private final LoginRepository loginRepository;
    private final RepositoryPhyEdProf repositorPhyEdProf;
    private final JwtService jwtService;
    private final AuthenticationManager autenticationManager;
    private final PasswordEncoder passwordEncoder;

    public LoginPhyEduProfService(LoginRepository loginRepository, RepositoryPhyEdProf repositorPhyEdProf,
            AuthenticationManager autenticationManager, PasswordEncoder passwordEncoder, JwtService jwtService) {
        this.loginRepository = loginRepository;
        this.repositorPhyEdProf = repositorPhyEdProf;
        this.autenticationManager = autenticationManager;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public LoginResponse createLogin(RegisterProfRequest request) {

        if (loginRepository.existsByCref(request.cref()) || loginRepository.existsByEmail(request.email())) {
            throw new RuntimeException("Cref or Email already registered");
        }

        LoginPhyEdProf loginPhyEdProf = new LoginPhyEdProf();
        loginPhyEdProf.setEmail(request.email());
        loginPhyEdProf.setCref(request.cref());
        loginPhyEdProf.setPassword(request.password());
        loginPhyEdProf.setPassword(passwordEncoder.encode(request.password()));

        LoginPhyEdProf saved = loginRepository.save(loginPhyEdProf);

        return new LoginResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getCref());
    }

    public String authenticate(LoginRequest request) {

        Optional<LoginPhyEdProf> user = loginRepository.findByEmail(request.login());

        if (user.isEmpty()) {
            user = loginRepository.findByCref(request.login());
        }

        LoginPhyEdProf login = user.orElseThrow(() -> new RuntimeException("User not_found"));

        if (!passwordEncoder.matches(request.password(), login.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        try {
            autenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.login(),
                            request.password()));
        } catch (Exception e) {
            throw new RuntimeException("Authentication failed", e);
        }

        Optional<PhysicalEducationProfessional> phyEdProf = repositorPhyEdProf.findByCref(login.getCref());

        String professionalId = phyEdProf
                .map(PhysicalEducationProfessional::getId)
                .orElse(null);

        Role role = phyEdProf
                .map(PhysicalEducationProfessional::getRole)
                .orElse(Role.PERSONAL_TRAINER);
        return jwtService.generateToken(
                login.getEmail(),
                login.getCref(),
                login.getId(),
                role);
    }

}
