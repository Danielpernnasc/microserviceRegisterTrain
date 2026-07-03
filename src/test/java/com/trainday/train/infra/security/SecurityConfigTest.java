package com.trainday.train.infra.security;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.web.servlet.MockMvc;

import com.trainday.train.api.controller.TrainController;
import com.trainday.train.api.controller.TrainTemplateController;
import com.trainday.train.application.TrainScheduleExerciseService;
import com.trainday.train.application.TrainService;
import com.trainday.train.application.TrainTemplateService;
import com.trainday.train.infra.config.SecurityConfig;
import com.trainday.train.infra.service.JwtService;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = {
                TrainController.class,
                TrainTemplateController.class
})
@ContextConfiguration(classes = {
                TrainController.class,
                TrainTemplateController.class,
                SecurityConfig.class,
                JwtAuthFilter.class
})
public class SecurityConfigTest {

        @Autowired
        MockMvc mockMvc;

        @MockBean
        TrainService trainService;

        @MockBean
        TrainTemplateService trainTemplateService;

        @MockBean
        TrainScheduleExerciseService trainScheduleExerciseService;

        @MockBean
        JwtService jwtService;

        @MockBean
        UserDetailsService userDetailsService;

        @BeforeEach
        void setUp() {
                when(jwtService.isTokenValid(anyString())).thenReturn(true);
                when(jwtService.extractUserName(anyString())).thenReturn("athlete@test.com");
                when(userDetailsService.loadUserByUsername(anyString()))
                                .thenReturn(User.withUsername("athlete@test.com")
                                                .password("password")
                                                .authorities("ROLE_ATHLETE")
                                                .build());
        }

        @Test
        void shouldAllowPublicTemplatesEndpointWithoutToken() throws Exception {
                mockMvc.perform(get("/trainTemplate/templates"))
                                .andExpect(status().isOk());
        }

        @Test
        void shouldRequireAuthenticationForCreateTrain() throws Exception {
                mockMvc.perform(post("/train")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isForbidden());
        }

        @Test
        void shouldRequireAuthenticationForProtectedTrainEndpoints() throws Exception {
                mockMvc.perform(get("/train/my-trains/1"))
                                .andExpect(status().isForbidden());

                mockMvc.perform(put("/train/my-trains/1")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isForbidden());

                mockMvc.perform(delete("/train/my-trains/1"))
                                .andExpect(status().isForbidden());

                mockMvc.perform(patch("/train/my-trains/1/schedule/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isForbidden());

                mockMvc.perform(patch("/train/my-trains/1/schedule/0/exercise/0")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}"))
                                .andExpect(status().isForbidden());
        }

        @Test
        void shouldAllowProtectedEndpointsWithValidBearerToken() throws Exception {
                mockMvc.perform(patch("/train/my-trains/999.999.999-99")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("{}")
                                .header("Authorization", "Bearer valid-token"))
                                .andExpect(status().isOk());

                mockMvc.perform(post("/trainTemplate/templates/1/apply")
                                .header("Authorization", "Bearer valid-token"))
                                .andExpect(status().isCreated());
        }
}
