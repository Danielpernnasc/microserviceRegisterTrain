package com.trainday.train.application;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
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
public class TrainServiceTest {

    @InjectMocks
    TrainService trainService;

    @Mock
    TrainRepository trainRepository;

    

    @Test
    void shouldCreateTrain() {

        TrainRequest req = new TrainRequest(
            "Treino A",
            "Força",
            "Treino focado em força para membros superiores",
            LocalDateTime.now(),
            List.of(new TrainScheduleRequest(
                "Segunda-feira",
                "Peito",
                "Força",
                List.of(new ExerciseRequest(
                    "Supino Reto",
                    4,
                    "10-12",
                    "60",
                    "Use uma barra de 20kg e aumente o peso progressivamente"
                ))
            ))
        );


        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Train create = trainService.createTrain(req, "athlete123");

        assertNotNull(create);
        assertEquals("Treino A", create.getNameTrain());
        assertEquals("athlete123", create.getAthleteId());
        assertEquals("Força", create.getCategory());
        assertEquals("Treino focado em força para membros superiores", create.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), create.getCreatedAt().getDayOfYear());
        assertEquals(1, create.getSchedules().size());
        assertEquals("Segunda-feira", create.getSchedules().get(0).getWeekday());
        assertEquals("Peito", create.getSchedules().get(0).getMusclegroup());
        assertEquals("Força", create.getSchedules().get(0).getEmphasis());
        assertEquals(1, create.getSchedules().get(0).getExercise().size());
        assertEquals("Supino Reto", create.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, create.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10-12", create.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", create.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use uma barra de 20kg e aumente o peso progressivamente", create.getSchedules().get(0).getExercise().get(0).getObservation());

    }

    @Test
    void shouldlistTrain(){

        Train train = new Train(
            "1",
            "athlete123",
            "Treino A",
            "Força",
            "Treino focado em força para membros superiores",
            LocalDateTime.now(),
            List.of(new TrainSchedule(
                "Segunda-feira",
                "Peito",
                "Força",
                List.of(new Exercise(
                    "Supino Reto",
                    4,
                    "10-12",
                    "60",
                    "Use uma barra de 20kg e aumente o peso progressivamente"
                ))
            ))
        );

        when(trainRepository.findAll()).thenReturn(List.of(train));

        Train found = trainService.listTrains().get(0);
            
        assertNotNull(found);
        assertEquals("Treino A", found.getNameTrain());
        assertEquals("athlete123", found.getAthleteId());
        assertEquals("Força", found.getCategory());
        assertEquals("Treino focado em força para membros superiores", found.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), found.getCreatedAt().getDayOfYear());
        assertEquals(1, found.getSchedules().size());
        assertEquals("Segunda-feira", found.getSchedules().get(0).getWeekday());
        assertEquals("Peito", found.getSchedules().get(0).getMusclegroup());
        assertEquals("Força", found.getSchedules().get(0).getEmphasis());
        assertEquals(1, found.getSchedules().get(0).getExercise().size());
        assertEquals("Supino Reto", found.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, found.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10-12", found.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", found.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use uma barra de 20kg e aumente o peso progressivamente", found.getSchedules().get(0).getExercise().get(0).getObservation());

    }

    @Test
    void shouldgetTrainforId(){
        Train train = new Train(
            "1",
            "athlete123",
            "Treino A",
            "Força",
            "Treino focado em força para membros superiores",
            LocalDateTime.now(),
            List.of(new TrainSchedule(
                "Segunda-feira",
                "Peito",
                "Força",
                List.of(new Exercise(
                    "Supino Reto",
                    4,
                    "10-12",
                    "60",
                    "Use uma barra de 20kg e aumente o peso progressivamente"
                ))
            ))
        );

       when(trainRepository.findById("1")).thenReturn(java.util.Optional.of(train));
       
       Train found = trainService.getTrainById("1");
         assertNotNull(found);
        assertEquals("Treino A", found.getNameTrain());
        assertEquals("athlete123", found.getAthleteId());
        assertEquals("Força", found.getCategory());
        assertEquals("Treino focado em força para membros superiores", found.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), found.getCreatedAt().getDayOfYear());
        assertEquals(1, found.getSchedules().size());
        assertEquals("Segunda-feira", found.getSchedules().get(0).getWeekday());
        assertEquals("Peito", found.getSchedules().get(0).getMusclegroup());
        assertEquals("Força", found.getSchedules().get(0).getEmphasis());
        assertEquals(1, found.getSchedules().get(0).getExercise().size());
        assertEquals("Supino Reto", found.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, found.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("10-12", found.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("60", found.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Use uma barra de 20kg e aumente o peso progressivamente", found.getSchedules().get(0).getExercise().get(0).getObservation());     

    }

    @Test
    void shouldPatchTrainById(){
        Train train = new Train(
                  "1",
            "athlete123",
            "Treino A",
            "Força",
            "Treino focado em força para membros superiores",
            LocalDateTime.now(),
            List.of(new TrainSchedule(
                "Segunda-feira",
                "Peito",
                "Força",
                List.of(new Exercise(
                    "Supino Reto",
                    4,
                    "10-12",
                    "60",
                    "Use uma barra de 20kg e aumente o peso progressivamente"
                ))
            ))
        );

         when(trainRepository.findById("1")).thenReturn(Optional.of(train));
        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

      TrainRequest patchReq = new TrainRequest(
        "Treino C",  // só isso muda
        null,
        null,
        null,
        null
    );

     Train result = trainService.patchTrainById("1", patchReq);

    assertNotNull(result);
    assertEquals("Treino C", result.getNameTrain());         // mudou
    assertEquals("Força", result.getCategory());              // manteve
    assertEquals("athlete123", result.getAthleteId());        // manteve
    assertEquals(1, result.getSchedules().size());            // manteve

    }

    @Test
    void shouldPatchTrainByIdWithAllFields(){
        Train existingTrain = new Train();
        existingTrain.setId("1");
        existingTrain.setAthleteId("athlete123");
        existingTrain.setNameTrain("Treino A");
        existingTrain.setCategory("Força");
        existingTrain.setDescription("Descrição antiga");
        existingTrain.setCreatedAt(LocalDateTime.now());
        existingTrain.setSchedules(List.of());

        when(trainRepository.findById("1")).thenReturn(Optional.of(existingTrain));
        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Agora passa todos os campos preenchidos
        TrainRequest patchReq = new TrainRequest(
        "Treino C",
        "Hipertrofia",
        "Descrição nova",
        null,
        List.of(new TrainScheduleRequest(
            "Terça-feira",
            "Perna",
            "Hipertrofia",
            List.of(new ExerciseRequest(
                "Agachamento Livre",
                4,
                "8-10",
                "90",
                "Mantenha a postura correta"
            ))
        ))
    );

    Train result = trainService.patchTrainById("1", patchReq);

    assertNotNull(result);
    assertEquals("Treino C", result.getNameTrain());
    assertEquals("Hipertrofia", result.getCategory());
    assertEquals("Descrição nova", result.getDescription());
    assertEquals(1, result.getSchedules().size());
    assertEquals("Terça-feira", result.getSchedules().get(0).getWeekday());
    assertEquals("Perna", result.getSchedules().get(0).getMusclegroup());

    }

    @Test
    void shouldUpdateTrain(){
        Train existtrain = new Train();

        existtrain.setId("1");
        existtrain.setAthleteId("athlete123");
        existtrain.setNameTrain("Treino A");
        existtrain.setCategory("Força");
        existtrain.setDescription("Treino focado em força para membros superiores");
        existtrain.setCreatedAt(LocalDateTime.now());
        existtrain.setSchedules(List.of(new TrainSchedule(
            "Segunda-feira",
            "Peito",
            "Força",
            List.of(new Exercise(
                "Supino Reto",
                4,
                "10-12",
                "60",       
                "Use uma barra de 20kg e aumente o peso progressivamente"
            ))
        )));

        when(trainRepository.findById("1")).thenReturn(Optional.of(existtrain));
        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Train result = trainService.updateTrainById("1", new TrainRequest(
            "Treino B",
            "Hipertrofia",
            "Treino focado em hipertrofia para membros inferiores",
            LocalDateTime.now(),
            List.of(new TrainScheduleRequest(
                "Terça-feira",
                "Perna",
                "Hipertrofia",
                List.of(new ExerciseRequest(
                    "Agachamento Livre",
                    4,
                    "8-10",
                    "90",
                    "Mantenha a postura correta e aumente  o peso progressivamente"
                ))
            )
        )));
        

        assertNotNull(result);
        assertEquals("Treino B", result.getNameTrain());
        assertEquals("athlete123", result.getAthleteId());
        assertEquals("Hipertrofia", result.getCategory());
        assertEquals("Treino focado em hipertrofia para membros inferiores", result.getDescription());
        assertEquals(LocalDateTime.now().getDayOfYear(), result.getCreatedAt().getDayOfYear());
        assertEquals(1, result.getSchedules().size());
        assertEquals("Terça-feira", result.getSchedules().get(0).getWeekday());
        assertEquals("Perna", result.getSchedules().get(0).getMusclegroup());
        assertEquals("Hipertrofia", result.getSchedules().get(0).getEmphasis());
        assertEquals(1, result.getSchedules().get(0).getExercise().size());
        assertEquals("Agachamento Livre", result.getSchedules().get(0).getExercise().get(0).getNameExercise());
        assertEquals(4, result.getSchedules().get(0).getExercise().get(0).getSeries());
        assertEquals("8-10", result.getSchedules().get(0).getExercise().get(0).getRepetitions());
        assertEquals("90", result.getSchedules().get(0).getExercise().get(0).getBreakTime());
        assertEquals("Mantenha a postura correta e aumente  o peso progressivamente", result.getSchedules().get(0).getExercise().get(0).getObservation());  
        

    }


    @Test
    void shouldDeleteTrain(){
        Train existtrain = new Train();
        
        existtrain.setId("1");
        existtrain.setAthleteId("athlete123");
        existtrain.setNameTrain("Treino A");
        existtrain.setCategory("Força");
        existtrain.setDescription("Treino focado em força para membros superiores");
        existtrain.setCreatedAt(LocalDateTime.now());
        existtrain.setSchedules(List.of(new TrainSchedule(
            "Segunda-feira",
            "Peito",
            "Força",
            List.of(new Exercise(
                "Supino Reto",
                4,
                "10-12",
                "60",       
                "Use uma barra de 20kg e aumente o peso progressivamente"
            ))
        )));

       when(trainRepository.findById("1")).thenReturn(Optional.of(existtrain));
   

       doNothing().when(trainRepository).delete(any(Train.class));

       assertDoesNotThrow(() -> trainService.deleteTrainById("1"));

       verify(trainRepository).delete(any(Train.class));
       
    }
       





}
