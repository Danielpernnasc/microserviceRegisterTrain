package com.trainday.train.infra.service;

import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.LoginRepository;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class LoginUserDetailsServiceTest {
    @Mock
    RepositoryPhyEdProf repositoryPhyEdProf;

    @Mock
    LoginRepository loginRepository;

    @InjectMocks
    LoginUserDetailsService loginUserDetailsService;

    @BeforeEach
    void setup() {
        loginUserDetailsService =
                new LoginUserDetailsService(loginRepository);
    }

    @Test
    void shouldLoadByUserName(){
        LoginPhyEdProf login = new LoginPhyEdProf();
        login.setId("User1");
        login.setName("Daniel Nassau");
        login.setCref("CREF123456-G-SP");
        login.setEmail("daniel.nassau@example.com");
        login.setPassword("123456");
        login.setRole(Role.PERSONAL_TRAINER);

        when(loginRepository.findByEmail("daniel.nassau@example.com"))
                .thenReturn(Optional.of(login));


        UserDetails userDetails =
                loginUserDetailsService.loadUserByUsername("daniel.nassau@example.com");


        assertEquals("daniel.nassau@example.com", userDetails.getUsername());
        assertEquals("123456", userDetails.getPassword());
    }

    @Test
    void shouldThrowWhenUserNotFound() {
        when(loginRepository.findByEmail("daniel.nassau@example.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                UsernameNotFoundException.class,
                () -> loginUserDetailsService.loadUserByUsername("daniel.nassau@example.com")
        );
    }

}
