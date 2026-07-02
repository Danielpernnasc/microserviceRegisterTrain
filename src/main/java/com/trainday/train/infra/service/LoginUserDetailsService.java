package com.trainday.train.infra.service;

import java.util.Optional;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.repository.LoginRepository;

@Service
public class LoginUserDetailsService implements UserDetailsService {

    private final LoginRepository loginRepository;
    private final static String Professional_not_found = "Professional not found";

    public LoginUserDetailsService(LoginRepository loginRepository) {
        this.loginRepository = loginRepository;
    }

    // @Override
    // public UserDetails loadUserByUsername(String login) throws
    // UsernameNotFoundException {

    // Optional<LoginPhyEdProf> user = loginRepository.findByCref(login);

    // if (user.isEmpty()) {
    // user = loginRepository.findByCref(login);
    // }

    // LoginPhyEdProf pep = user.orElseThrow(
    // () -> new UsernameNotFoundException(Professional_not_found));

    // return User.builder()
    // .username(pep.getCref() != null ? pep.getCref() : pep.getEmail())
    // .password(pep.getPassword())
    // .authorities("USER")
    // .build();
    // }

    @Override
    public UserDetails loadUserByUsername(String login) {

        System.out.println("LOAD USER = " + login);

        Optional<LoginPhyEdProf> user = loginRepository.findByEmail(login);

        System.out.println("ACHOU EMAIL = " + user.isPresent());

        if (user.isEmpty()) {
            user = loginRepository.findByCref(login);

            System.out.println("ACHOU CREF = " + user.isPresent());
        }

        LoginPhyEdProf loginPhyEdProf = user.orElseThrow(
                () -> new UsernameNotFoundException("Professional not found"));

        System.out.println("USUARIO RETORNADO = " + loginPhyEdProf.getEmail());

        return User.builder()
                .username(loginPhyEdProf.getCref())
                .username(loginPhyEdProf.getEmail())
                .password(loginPhyEdProf.getPassword())
                .roles("USER")
                .build();
    }
}
