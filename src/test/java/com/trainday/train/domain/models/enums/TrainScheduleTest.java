package com.trainday.train.domain.models.enums;

import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.TrainSchedule;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

public class TrainScheduleTest {

    @Test
    void shouldAddExerciseToSchedule() {
        TrainSchedule schedule = new TrainSchedule();
        Exercise exercise = new Exercise("Supino Reto", 4, "10-12", "60", "Controle de movimento");

        schedule.addExercise(exercise);

        assertEquals(1, schedule.getExercises().size());
        assertSame(exercise, schedule.getExercises().get(0));
    }
}
