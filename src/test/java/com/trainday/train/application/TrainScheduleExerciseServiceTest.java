package com.trainday.train.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.repository.TrainRepository;

@ExtendWith(MockitoExtension.class)
public class TrainScheduleExerciseServiceTest {

    @InjectMocks
    TrainScheduleExerciseService trainScheduleExerciseService;

    @Mock
    TrainRepository trainRepository;

    @Test
    void ShouldpatchTrainScheduleById(){
        Train existingTrain = new Train();
        existingTrain.setId("1");
        existingTrain.setAthleteId("athlete123");
        existingTrain.setNameTrain("Treino A");
        existingTrain.setCategory("Força");
        existingTrain.setDescription("Descrição antiga");
        existingTrain.setCreatedAt(LocalDateTime.now());
        existingTrain.setSchedules(List.of());

        TrainSchedule existingSchedule = new TrainSchedule();
        existingSchedule.setWeekday("Segunda-Feira");
        existingSchedule.setMusclegroup("Peito");
        existingSchedule.setEmphasis("Volume");
        existingSchedule.setExercises(List.of());

        existingTrain.setSchedules(new ArrayList<>(List.of(existingSchedule)));

   
      
    
         TrainScheduleRequest patchTrainScheduleRequest = new TrainScheduleRequest(
            "Quarta-Feira",
            "Peito",
            "Volume", 
            List.of(new ExerciseRequest(
               null,
               null,
                null,
                null,
                null
            )) 
    
    );
    
    when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));
    when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Train result = trainScheduleExerciseService.patchTrainScheduleById("1", 0, patchTrainScheduleRequest);

    assertNotNull(result);
     assertEquals("Quarta-Feira",  result.getSchedules().get(0).getWeekday());
        assertEquals("Peito",  result.getSchedules().get(0).getMusclegroup());
        assertEquals("Volume",  result.getSchedules().get(0).getEmphasis());
      

  }

  @Test
  void shouldThrowWhenScheduleIndexOutOfBounds() {
        Train existingTrain = new Train();
        existingTrain.setId("1");
        existingTrain.setSchedules(new ArrayList<>(List.of()));

        when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));

        TrainScheduleRequest req = new TrainScheduleRequest(
            "Segunda-Feira", null, null, List.of()
        );

        assertThrows(RuntimeException.class, () -> {
            trainScheduleExerciseService.patchTrainScheduleById("1", 99, req);
        });
    }

    @Test
    void shouldpatchTrainExercise(){
        Train existingTrain = new Train();
        existingTrain.setId("1");
        existingTrain.setAthleteId("athlete123");
        existingTrain.setNameTrain("Treino A");
        existingTrain.setCategory("Força");
        existingTrain.setDescription("Descrição antiga");
        existingTrain.setCreatedAt(LocalDateTime.now());
        existingTrain.setSchedules(List.of());

        TrainSchedule existingSchedule = new TrainSchedule();
        existingSchedule.setWeekday("Segunda-Feira");
        existingSchedule.setMusclegroup("Peito");
        existingSchedule.setEmphasis("Volume");
        existingSchedule.setExercises(List.of());
        existingTrain.setSchedules(new ArrayList<>(List.of(existingSchedule)));

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino Inclinado");
        exercise.setSeries(4);
        exercise.setRepetitions("6-8");
        exercise.setBreakTime("90s");
        exercise.setObservation("Descida de devagar e explosão na subida");
        existingSchedule.setExercises(new ArrayList<>(List.of(exercise)));

        ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino Reto",
             4,
            "6-10",
            "90s", 
            "Descida de devagar e explosão na subida"
        );

        when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));
        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Train result = trainScheduleExerciseService.patchTrainExercise("1", 0, 0, exerciseRequest);
        assertNotNull(result);
        assertEquals("Supino Reto", result.getSchedules().get(0).getExercises().get(0).getNameExercise());
        assertEquals(4, result.getSchedules().get(0).getExercises().get(0).getSeries());
        assertEquals("6-10", result.getSchedules().get(0).getExercises().get(0).getRepetitions());
        assertEquals("90s", result.getSchedules().get(0).getExercises().get(0).getBreakTime());
        assertEquals("Descida de devagar e explosão na subida", result.getSchedules().get(0).getExercises().get(0).getObservation());
        
    }

      @Test
      void shouldThrowWhenExerciseIndexOutOfBounds() {
            Train existingTrain = new Train();
            existingTrain.setId("1");
            existingTrain.setSchedules(new ArrayList<>(List.of()));

            when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));

            ExerciseRequest req = new ExerciseRequest(
                "Supino Reto", null, null, null, null
            );

            assertThrows(RuntimeException.class, () -> {
                trainScheduleExerciseService.patchTrainExercise("1", 0, 0, req);
            });
        }

        @Test
        void shouldThrowWhenExercise_IndexOutOfBounds() {
            TrainSchedule trainSchedule = new TrainSchedule();
            trainSchedule.setWeekday("Segunda-Feira");
            trainSchedule.setExercises(new ArrayList<>());

            Train existingTrain = new Train();
            existingTrain.setId("1");
            existingTrain.setSchedules(new ArrayList<>(List.of(trainSchedule)));

            when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));

            ExerciseRequest req = new ExerciseRequest(
                "Supino Reto", null, null, null, null
            );

            assertThrows(RuntimeException.class, () -> {
                trainScheduleExerciseService.patchTrainExercise("1", 0, 99, req);
            });

        }






}
