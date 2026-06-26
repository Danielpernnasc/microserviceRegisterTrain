package com.trainday.train.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.OrRequestMatcher;
import static org.springframework.security.web.util.matcher.RegexRequestMatcher.regexMatcher;
import com.trainday.train.infra.security.JwtAuthFilter;

@Configuration
public class SecurityConfig {

    private static final String TRAINS = "/train/**";
    private static final String TRAIN_MY_TRAINS_BY_ID = "/train/my-trains/**";
    private static final String TRAIN_SCHEDULE = "/train/my-trains/*/schedule/*";
    private static final String TRAIN_SCHEDULE_EXERCISE = "/train/my-trains/*/schedule/*/exercise/*";
    private static final String TRAIN_TEMPLATES = "/trainTemplate/templates";
    private static final String APPLY_TRAIN_TEMPLATE = "/trainTemplate/templates/*/apply";
    private static final String AUTH = "/auth/*";
    private static final String PEP = "/PEP";
    private static final String PEP_CREF = "/PEP/*";

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter) {
        this.jwtAuthFilter = jwtAuthFilter;
    }

    @Bean
    @Order(1)
    public SecurityFilterChain swaggerFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher(swaggerRequestMatcher())
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().permitAll())
                .build();
    }

    @Bean
    @Order(2)
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http

                .csrf(csrf -> csrf.disable())

                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

                .authorizeHttpRequests(auth -> auth

                        // Swagger
                        .requestMatchers(
                                "/swagger-ui/**",
                                "/v3/api-docs/**",
                                "/swagger-ui.html")
                        .permitAll()

                        // Train endpoints públicos
                        .requestMatchers(HttpMethod.POST, TRAINS).authenticated()
                        .requestMatchers(HttpMethod.GET, TRAIN_MY_TRAINS_BY_ID).authenticated()
                        .requestMatchers(HttpMethod.PUT, TRAIN_MY_TRAINS_BY_ID).authenticated()
                        .requestMatchers(HttpMethod.DELETE, TRAIN_MY_TRAINS_BY_ID).authenticated()
                        .requestMatchers(HttpMethod.PATCH, TRAIN_SCHEDULE).authenticated()
                        .requestMatchers(HttpMethod.PATCH, TRAIN_SCHEDULE_EXERCISE).authenticated()

                        .requestMatchers(HttpMethod.GET, TRAIN_TEMPLATES).permitAll()
                        .requestMatchers(HttpMethod.POST, APPLY_TRAIN_TEMPLATE).authenticated()

                        .requestMatchers(HttpMethod.POST, AUTH).permitAll()

                        .requestMatchers(HttpMethod.POST, PEP).authenticated()
                        .requestMatchers(HttpMethod.GET, PEP_CREF).authenticated()
                        .requestMatchers(HttpMethod.PUT, PEP_CREF).authenticated()
                        .requestMatchers(HttpMethod.PATCH, PEP_CREF).authenticated()
                        .requestMatchers(HttpMethod.DELETE, PEP_CREF).authenticated()

                        .anyRequest().authenticated()

                )

                .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

                .build();
    }

    private OrRequestMatcher swaggerRequestMatcher() {
        return new OrRequestMatcher(
                regexMatcher("^/v3/api-docs(/.*)?$"),
                regexMatcher("^/swagger-ui(/.*)?$"),
                regexMatcher("^/swagger-ui\\.html$"),
                regexMatcher("^/swagger-resources(/.*)?$"),
                regexMatcher("^/webjars(/.*)?$"));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return (AuthenticationManager) config.getAuthenticationManager();
    }

}