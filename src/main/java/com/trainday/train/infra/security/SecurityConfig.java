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

    private static final String TRAINS = "/train/**";
    private static final String TRAIN_MY_TRAINS = "/train/my-trains";
    private static final String TRAIN_MY_TRAINS_BY_ID = "/train/my-trains/**";
    private static final String TRAIN_SCHEDULE = "/train/my-trains/*/schedule/*";
    private static final String TRAIN_SCHEDULE_EXERCISE = "/train/my-trains/*/schedule/*/exercise/*";
    private static final String TRAIN_TEMPLATES = "/trainTemplate/templates";
    private static final String APPLY_TRAIN_TEMPLATE = "/trainTemplate/templates/*/apply";

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
                .requestMatchers(HttpMethod.POST, TRAINS).authenticated()
                .requestMatchers(HttpMethod.GET, TRAIN_MY_TRAINS).permitAll()
                .requestMatchers(HttpMethod.GET, TRAIN_MY_TRAINS_BY_ID).authenticated()
                .requestMatchers(HttpMethod.PUT, TRAIN_MY_TRAINS_BY_ID).authenticated()
                .requestMatchers(HttpMethod.DELETE, TRAIN_MY_TRAINS_BY_ID).authenticated()
                .requestMatchers(HttpMethod.PATCH, TRAIN_SCHEDULE).authenticated()
                .requestMatchers(HttpMethod.PATCH, TRAIN_SCHEDULE_EXERCISE).authenticated()

                .requestMatchers(HttpMethod.GET, TRAIN_TEMPLATES).permitAll()
                .requestMatchers(HttpMethod.POST, APPLY_TRAIN_TEMPLATE).authenticated()


                // qualquer outro endpoint exige token
                .anyRequest().authenticated()
                
              
            )

            .addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class)

            .build();
    }

}
