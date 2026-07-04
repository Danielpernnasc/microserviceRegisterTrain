package com.trainday.train.infra.config;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.data.mongo.MongoRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import com.trainday.train.infra.security.JwtAuthFilter;

import static org.mockito.Mockito.mock;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
@ContextConfiguration(classes = {
        SecurityConfig.class,
        SecurityConfigTest.TestBeans.class
})
@EnableAutoConfiguration(exclude = {
        MongoAutoConfiguration.class,
        MongoDataAutoConfiguration.class,
        MongoRepositoriesAutoConfiguration.class
})
@DisplayName("SecurityConfig Tests")
public class SecurityConfigTest {

        @Autowired
        private MockMvc mockMvc;

        // ========== URLs Constants ==========
        private static final String SWAGGER_UI = "/swagger-ui.html";
        private static final String API_DOCS = "/v3/api-docs";
        private static final String SWAGGER_RESOURCES = "/swagger-resources";
        private static final String AUTH_LOGIN = "/auth/login";
        private static final String AUTH_REGISTER = "/auth/register";
        private static final String TRAIN = "/train/123";
        private static final String TRAIN_ATHLETE = "/train/athlete/MyTrain/123";
        private static final String TRAIN_SEARCH = "/train/athlete/train/123";
        private static final String TRAIN_TEMPLATE = "/trainTemplate/templates";
        private static final String TRAIN_TEMPLATE_APPLY = "/trainTemplate/templates/123/apply";
        private static final String PEP = "/PEP";
        private static final String PEP_CREF = "/PEP/123";
        private static final String UNKNOWN_ENDPOINT = "/unknown/endpoint";

        // ========== SWAGGER - Public ==========
        @Test
        @DisplayName("Swagger UI deve ser acessível publicamente")
        void swaggerUiDeveSerPublico() throws Exception {
                mockMvc.perform(get(SWAGGER_UI))
                        .andExpect(status().is(404));
        }

        @Test
        @DisplayName("API Docs deve ser acessível publicamente")
        void apiDocsDeveSerPublico() throws Exception {
                mockMvc.perform(get(API_DOCS))
                        .andExpect(status().is(404));
        }

        @Test
        @DisplayName("Swagger Resources deve ser acessível publicamente")
        void swaggerResourcesDeveSerPublico() throws Exception {
                mockMvc.perform(get(SWAGGER_RESOURCES))
                        .andExpect(status().is(404));
        }

        // ========== AUTH - Public ==========
        @ParameterizedTest
        @CsvSource({
                "POST," + AUTH_LOGIN,
                "POST," + AUTH_REGISTER
        })
        @DisplayName("Auth endpoints devem ser públicos")
        void authEndpointsDeveSerPublico(String method, String url) throws Exception {
                var request = method.equals("POST") ? post(url) : get(url);
                mockMvc.perform(request)
                        .andExpect(status().is(404)); // Sem dados, mas não 401/403
        }

        // ========== TRAIN - Requires Authentication ==========
        @Test
        @DisplayName("POST /train sem autenticação retorna 401")
        void trainPostSemAutenticacaoDeveRetornar401() throws Exception {
                mockMvc.perform(post(TRAIN))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("POST /train com PERSONAL_TRAINER é permitido")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void trainPostComPersonalTrainerEhPermitido() throws Exception {
                mockMvc.perform(post(TRAIN))
                        .andExpect(status().isNotFound()); // Controller não existe
        }

        @Test
        @DisplayName("POST /train com ATHLETE retorna 403")
        @WithMockUser(roles = "ATHLETE")
        void trainPostComAthleteRetornaForbidden() throws Exception {
                mockMvc.perform(post(TRAIN))
                        .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("GET /train/athlete/train/{cpf} sem autenticação retorna 401")
        void trainSearchSemAutenticacaoRetorna403() throws Exception {
                mockMvc.perform(get(TRAIN_SEARCH))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("GET /train/athlete/train/{cpf} com PERSONAL_TRAINER é permitido")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void trainSearchComPersonalTrainerEhPermitido() throws Exception {
                mockMvc.perform(get(TRAIN_SEARCH))
                        .andExpect(status().isNotFound());
        }

        // ========== TRAIN ATHLETE - Requires ATHLETE role ==========
        @Test
        @DisplayName("GET /train/athlete/MyTrain/* sem autenticação retorna 401")
        void trainAthleteGetSemAutenticacaoRetorna403() throws Exception {
                mockMvc.perform(get(TRAIN_ATHLETE))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("GET /train/athlete/MyTrain/* com ATHLETE é permitido")
        @WithMockUser(roles = "ATHLETE")
        void trainAthleteGetComAthleteEhPermitido() throws Exception {
                mockMvc.perform(get(TRAIN_ATHLETE))
                        .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("GET /train/athlete/MyTrain/* com PERSONAL_TRAINER retorna 403")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void trainAthleteGetComPersonalTrainerRetornaForbidden() throws Exception {
                mockMvc.perform(get(TRAIN_ATHLETE))
                        .andExpect(status().isForbidden());
        }

        // ========== TRAIN TEMPLATES ==========
        @Test
        @DisplayName("GET /trainTemplate/templates deve ser público")
        void trainTemplatesGetDeveSerPublico() throws Exception {
                mockMvc.perform(get(TRAIN_TEMPLATE))
                        .andExpect(status().is(404));
        }

        @Test
        @DisplayName("POST /trainTemplate/templates/*/apply sem autenticação retorna 401")
        void trainTemplatesApplySemAutenticacaoRetorna403() throws Exception {
                mockMvc.perform(post(TRAIN_TEMPLATE_APPLY))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("POST /trainTemplate/templates/*/apply com PERSONAL_TRAINER é permitido")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void trainTemplatesApplyComPersonalTrainerEhPermitido() throws Exception {
                mockMvc.perform(post(TRAIN_TEMPLATE_APPLY))
                        .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("POST /trainTemplate/templates/*/apply com ATHLETE retorna 403")
        @WithMockUser(roles = "ATHLETE")
        void trainTemplatesApplyComAthleteRetornaForbidden() throws Exception {
                mockMvc.perform(post(TRAIN_TEMPLATE_APPLY))
                        .andExpect(status().isForbidden());
        }

        // ========== PEP (Physical Education Professional) ==========
        @Test
        @DisplayName("POST /PEP sem autenticação retorna 401")
        void pepPostSemAutenticacaoRetorna403() throws Exception {
                mockMvc.perform(post(PEP))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("POST /PEP com PERSONAL_TRAINER é permitido")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void pepPostComPersonalTrainerEhPermitido() throws Exception {
                mockMvc.perform(post(PEP))
                        .andExpect(status().isNotFound());
        }

        @Test
        @DisplayName("DELETE /PEP/* com PERSONAL_TRAINER retorna 403")
        @WithMockUser(roles = "PERSONAL_TRAINER")
        void pepDeleteComPersonalTrainerRetornaForbidden() throws Exception {
                mockMvc.perform(delete(PEP_CREF))
                        .andExpect(status().isForbidden());
        }

        @Test
        @DisplayName("DELETE /PEP/* com ADMIN é permitido")
        @WithMockUser(roles = "ADMIN")
        void pepDeleteComAdminEhPermitido() throws Exception {
                mockMvc.perform(delete(PEP_CREF))
                        .andExpect(status().isNotFound());
        }

        // ========== Unknown Endpoints - Require Authentication ==========
        @Test
        @DisplayName("Endpoint desconhecido sem autenticação retorna 401")
        void endpointDesconhecidoSemAutenticacaoRetorna403() throws Exception {
                mockMvc.perform(get(UNKNOWN_ENDPOINT))
                        .andExpect(status().is(403));
        }

        @Test
        @DisplayName("Endpoint desconhecido com autenticação retorna 404")
        @WithMockUser
        void endpointDesconhecidoComAutenticacaoRetorna404() throws Exception {
                mockMvc.perform(get(UNKNOWN_ENDPOINT))
                        .andExpect(status().isNotFound());
        }

        // ========== Test Configuration ==========
        @TestConfiguration
        @EnableWebMvc
        @EnableWebSecurity
        static class TestBeans {
                @Bean
                UserDetailsService userDetailsService() {
                        return mock(UserDetailsService.class);
                }

                @Bean
                com.trainday.train.infra.service.JwtService jwtService() {
                        return mock(com.trainday.train.infra.service.JwtService.class);
                }

                @Bean
                JwtAuthFilter jwtAuthFilter(com.trainday.train.infra.service.JwtService jwtService) {
                        return new JwtAuthFilter(jwtService);
                }
        }
}