package com.trainday.train.domain.models;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TrainTest {



    @Test
    void shouldTestCreateTrain(){

        Train train = new Train();

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Bench Press");
        exercise.setSeries(4);
        exercise.setRepetitions("10");
        exercise.setBreakTime("60");
        exercise.setObservation("Use a weight that allows you to complete the repetitions with good form.");

        TrainSchedule schedule = new TrainSchedule();
        schedule.setWeekday("Monday");
        schedule.setMusclegroup("Chest and Triceps");
        schedule.setEmphasis("Hypertrophy");
        schedule.setExercise(List.of(exercise));


        train.setId("1");
        train.setAthleteId("1");
        train.setNameTrain("Daniel");
        train.setCategory("Natural Physique");
        train.setDescription("Train for natural physique");
        train.setCreatedAt(LocalDateTime.now());
        train.setSchedules(List.of(schedule));
     

        assertEquals("1", train.getId());
        assertEquals("1", train.getAthleteId());
        assertEquals("Daniel", train.getNameTrain());
        assertEquals("Natural Physique", train.getCategory());
        assertEquals("Train for natural physique", train.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), train.getCreatedAt().getDayOfYear());
        assertEquals(1, train.getSchedules().size());
        assertEquals("Monday", train.getSchedules().get(0).getWeekday());
        assertEquals("Chest and Triceps", train.getSchedules().get(0).getMusclegroup());
        assertEquals("Hypertrophy", train.getSchedules().get(0).getEmphasis());
        assertEquals(1, train.getSchedules().get(0).getExercise().size());
        assertEquals("Bench Press", train.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, train.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10", train.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", train.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use a weight that allows you to complete the repetitions with good form.", train.getSchedules().get(0).getExercise().get(0).getObservation());

    }

    @Test
    void shouldSetCreatedAtWhenNull(){
        Train train = new Train();
        train.setCreatedAt(null);

        train.prePersist();

        assertNotNull(train.getCreatedAt());
    }

    @Test
    void shouldTestTrain(){

        Exercise exercise = new Exercise(
            "Bench Press",
            4,
            "10",
            "60",
            "Use a weight that allows you to complete the repetitions with good form."
        );

        TrainSchedule schedule = new TrainSchedule(
            "Monday",
            "Chest and Triceps",
            "Hypertrophy",
            List.of(exercise)
        );



        Train train = new Train(
            "1",
            "1",
            "Chest and Triceps",
            "Natural Physique",
            "Treino Natural",
            LocalDateTime.now(),
            List.of(schedule)
        );

        assertEquals("1", train.getId());
        assertEquals("1", train.getAthleteId());
        assertEquals("Chest and Triceps", train.getNameTrain());
        assertEquals("Natural Physique", train.getCategory());
        assertEquals("Treino Natural", train.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), train.getCreatedAt().getDayOfYear());
        assertEquals(1, train.getSchedules().size());
        assertEquals("Monday", train.getSchedules().get(0).getWeekday());
        assertEquals("Chest and Triceps", train.getSchedules().get(0).getMusclegroup());
        assertEquals("Hypertrophy", train.getSchedules().get(0).getEmphasis());
        assertEquals(1, train.getSchedules().get(0).getExercise().size());
        assertEquals("Bench Press", train.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, train.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10", train.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", train.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use a weight that allows you to complete the repetitions with good form.", train.getSchedules().get(0).getExercise().get(0).getObservation());
    }

}
