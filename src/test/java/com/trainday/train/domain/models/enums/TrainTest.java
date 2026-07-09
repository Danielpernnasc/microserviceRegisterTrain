package com.trainday.train.domain.models.enums;

import com.trainday.train.domain.models.Train;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

public class TrainTest {
    @Test
    void shouldSetCreatedAtWhenNullOnPrePersist() {
        Train train = new Train();
        LocalDateTime before = LocalDateTime.now();

        train.prePersist();

        LocalDateTime createdAt = train.getCreatedAt();
        LocalDateTime after = LocalDateTime.now();

        assertNotNull(createdAt);
        assertTrue(!createdAt.isBefore(before) && !createdAt.isAfter(after));
    }

    @Test
    void shouldKeepCreatedAtWhenAlreadySetOnPrePersist() {
        Train train = new Train();
        LocalDateTime fixedCreatedAt = LocalDateTime.of(2026, 7, 6, 13, 58, 0);
        train.setCreatedAt(fixedCreatedAt);

        train.prePersist();

        assertEquals(fixedCreatedAt, train.getCreatedAt());
    }
}
