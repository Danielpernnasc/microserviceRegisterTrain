package com.trainday.train.infra.security;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

public class SwaggerConfigTest {
    @Test
    void shouldCreateSwaggerConfig() {
        SwaggerConfig swaggerConfig = new SwaggerConfig();
        assertNotNull(swaggerConfig);
    }

}
