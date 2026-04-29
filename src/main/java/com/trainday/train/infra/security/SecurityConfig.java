package com.trainday.train.infra.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
public class SecurityConfig {

    private final JwtAuthFilter jwtAuthFilter;

    public SecurityConfig(JwtAuthFilter jwtAuthFilter){
        this.jwtAuthFilter = jwtAuthFilter;
    }

     @Bean
      public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
          return http
  
            .csrf(csrf -> csrf.disable())
            
            .sessionManagement(session ->
                session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            
            .authorizeHttpRequests(auth -> auth
                
                // Swagger
                .requestMatchers(
                    "/swagger-ui/**",
                    "/v3/api-docs/**",
                    "/swagger-ui.html"
                ).permitAll()

                // Train endpoints públicos
                .requestMatchers(HttpMethod.POST, "/train/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/train/my-trains").permitAll()
                .requestMatchers(HttpMethod.GET, "/train/my-trains/**").authenticated()
                .requestMatchers(HttpMethod.PUT, "/train/my-trains/**").authenticated()
                .requestMatchers(HttpMethod.DELETE, "/train/my-trains/**").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/train/my-trains/*/schedule/*").authenticated()
                .requestMatchers(HttpMethod.PATCH, "/train/my-trains/*/schedule/*/exercise/*").authenticated()

                .requestMatchers(HttpMethod.GET, "/trainTemplate/templates").permitAll()
                .requestMatchers(HttpMethod.POST, "/trainTemplate/templates/*/apply").authenticated()


                // qualquer outro endpoint exige token
                .anyRequest().authenticated()
                
              
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            .build();
    }

}