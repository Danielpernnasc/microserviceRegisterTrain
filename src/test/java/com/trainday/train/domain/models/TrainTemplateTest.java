package com.trainday.train.domain.models;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

public class TrainTemplateTest {

    @Test
    public void ShouldTrainTemplateCreation() {

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

        TrainTemplate traintemplate = new TrainTemplate();

        traintemplate.setId("1");
        traintemplate.setNameTrain("Daniel");
        traintemplate.setCategory("Natural Physique");
        traintemplate.setDescription("Train for natural physique");
        traintemplate.setCreatedAt(LocalDateTime.now());
        traintemplate.setSchedules(List.of(schedule));  

        assertEquals("1", traintemplate.getId());
        assertEquals("Daniel", traintemplate.getNameTrain());
        assertEquals("Natural Physique", traintemplate.getCategory());
        assertEquals("Train for natural physique", traintemplate.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), traintemplate.getCreatedAt().getDayOfYear());
        assertEquals(1, traintemplate.getSchedules().size());
        assertEquals("Monday", traintemplate.getSchedules().get(0).getWeekday());
        assertEquals("Chest and Triceps", traintemplate.getSchedules().get(0).getMusclegroup());
        assertEquals("Hypertrophy", traintemplate.getSchedules().get(0).getEmphasis());
        assertEquals(1, traintemplate.getSchedules().get(0).getExercise().size());
        assertEquals("Bench Press", traintemplate.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, traintemplate.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10", traintemplate.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", traintemplate.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use a weight that allows you to complete the repetitions with good form.", traintemplate.getSchedules().get(0).getExercise().get(0).getObservation());
     
    }   

    @Test
    void shouldTrainTemplate(){

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



        TrainTemplate trainT = new TrainTemplate(
            "1",
            "Chest and Triceps",
            "Natural Physique",
            "Treino Natural",
            LocalDateTime.now(),
            List.of(schedule)
        );

        assertEquals("1", trainT.getId());
        assertEquals("Chest and Triceps", trainT.getNameTrain());
        assertEquals("Natural Physique", trainT.getCategory());
        assertEquals("Treino Natural", trainT.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), trainT.getCreatedAt().getDayOfYear());
        assertEquals(1, trainT.getSchedules().size());
        assertEquals("Monday", trainT.getSchedules().get(0).getWeekday());
        assertEquals("Chest and Triceps", trainT.getSchedules().get(0).getMusclegroup());
        assertEquals("Hypertrophy", trainT.getSchedules().get(0).getEmphasis());
        assertEquals(1, trainT.getSchedules().get(0).getExercise().size());
        assertEquals("Bench Press", trainT.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, trainT.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10", trainT.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", trainT.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use a weight that allows you to complete the repetitions with good form.", trainT.getSchedules().get(0).getExercise().get(0).getObservation());
    }



}
