package com.trainday.train.infra.security;

import java.io.IOException;
import java.util.List;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
 
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;


import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    public JwtAuthFilter(JwtService jwtService) {
        this.jwtService = jwtService;
    }

   @Override
   protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
                                    throws ServletException, IOException {

            String authHeader = request.getHeader("Authorization");

            if(authHeader == null || !authHeader.startsWith("Bearer ")){
                System.out.println("SEM TOKEN");
                filterChain.doFilter(request, response);
                return;
            }

            String token = authHeader.substring(7);

            System.out.println("VÁLIDANDO TOKEN...");

            try{

                if(jwtService.isTokenValid(token)){
                    String email = jwtService.extractEmail(token);

                    System.out.println("TOKEN OK: " + email);

                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(email, null, List.of());

                    SecurityContextHolder.getContext().setAuthentication((Authentication) auth);
                }

            } catch (Exception e){
                System.out.println("TOKEN INVÁLIDO: " + e.getMessage());
            }

            filterChain.doFilter(request, response);

    }
   


}
   
