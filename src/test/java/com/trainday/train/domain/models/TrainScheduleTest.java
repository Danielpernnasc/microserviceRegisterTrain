package com.trainday.train.domain.models;

import static org.junit.jupiter.api.Assertions.*;



import org.junit.jupiter.api.Test;

public class TrainScheduleTest {

    @Test
    void shouldAddExercise(){

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Bench Press");
        exercise.setSeries(4);
        exercise.setRepetitions("10");
        exercise.setBreakTime("60");
        exercise.setObservation("Use a weight that allows you to complete the repetitions with good form.");

        TrainSchedule schedule = new TrainSchedule();
        schedule.addExercise(exercise);
        
         assertEquals(1, schedule.getExercise().size());
          assertEquals("Bench Press", schedule.getExercise().get(0).getNameExercise());
    }

   

}
