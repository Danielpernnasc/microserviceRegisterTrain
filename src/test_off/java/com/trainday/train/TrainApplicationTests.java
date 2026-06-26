package com.trainday.train;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
class TrainApplicationTests {

 	@Test
    void shouldLoadContext() {
        assertNotNull(new TrainApplication());
    }

}
