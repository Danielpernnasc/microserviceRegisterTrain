package com.trainday.train.application;

import java.util.List;
import java.util.Optional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
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
        LoginPhyEdProf loginPhyEdProf = new LoginPhyEdProf();
        loginPhyEdProf.setEmail(request.email());
        loginPhyEdProf.setCref(request.cref());
        loginPhyEdProf.setPassword(request.password());
        loginPhyEdProf.setPassword(passwordEncoder.encode(request.password()));

        LoginPhyEdProf saved = loginRepository.save(loginPhyEdProf);

        return new LoginResponse(
                saved.getId(),
                saved.getEmail(),
                saved.getCref(),
                saved.getName());
    }

    public String authenticate(LoginRequest request) {

        System.out.println("PASSO 1 - INICIO");

        Optional<LoginPhyEdProf> user = loginRepository.findByEmail(request.login());

        System.out.println("PASSO 2 - EMAIL");

        if (user.isEmpty()) {
            user = loginRepository.findByCref(request.login());
        }

        System.out.println("PASSO 3 - CREF");

        LoginPhyEdProf login = user.orElseThrow(() -> new RuntimeException("User not found"));

        System.out.println("PASSO 4 - USUARIO ENCONTRADO");

        if (!passwordEncoder.matches(request.password(), login.getPassword())) {
            throw new RuntimeException("Invalid credentials");
        }

        System.out.println("PASSO 5 - SENHA OK");

        try {
            autenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.login(),
                            request.password()));
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }

        System.out.println("PASSO 6 - AUTH OK");

        Optional<PhysicalEducationProfessional> phyEdProf = repositorPhyEdProf.findByCref(login.getCref());

        System.out.println("PASSO 7 - PEP OK");

        String professionalId = phyEdProf
                .map(PhysicalEducationProfessional::getId)
                .orElse(null);

        Role role = phyEdProf
                .map(PhysicalEducationProfessional::getRole)
                .orElse(Role.PERSONAL_TRAINER);

        System.out.println("PASSO 8 - GERANDO TOKEN");

        return jwtService.generateToken(
                login.getEmail(),
                login.getCref(),
                login.getId(),
                professionalId,
                role);
    }

}
