package com.trainday.train.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.application.TrainScheduleExerciseService;
import com.trainday.train.application.TrainService;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.infra.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class TrainControllerTest {

    @Mock
    TrainService trainService;

    @Mock
    private JwtService jwtService;

    @InjectMocks
    TrainController trainController;

    @Mock
    TrainScheduleExerciseService trainScheduleExerciseService;

    @Test
    void ShouldCreateTrain(){

        ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino reto barra",
            4,
            "8-10",
            "90s",
            "1a leve, ultimas 2 ate falha"
        );

        TrainScheduleRequest trainScheduleReq  = new TrainScheduleRequest(
            "Segunda",
            "Peito e Ombros",
            "Volume e densidade peitoral",
            List.of(exerciseRequest)
        );

        LocalDateTime now = LocalDateTime.now();
        
        TrainRequest trainRequest = new TrainRequest(
            "Classic Elite Pro",
            "Classic Physique",
             "Divisao semanal avan‡ada estilo Classic Physique",
             now,
             List.of(trainScheduleReq)
        );

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino reto barra");
        exercise.setSeries(4);
        exercise.setRepetitions("8-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(new ArrayList<>(List.of(exercise)));

        Train train = new Train();
        train.setNameTrain("Classic Elite Pro");
        train.setAthleteId("dpericles6@gmail.com");
        train.setCategory("Classic Physique");
        train.setDescription("Divisao semanal avan‡ada estilo Classic Physique");
        train.setCreatedAt(now);
        train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

        Authentication  authentication = mock(Authentication.class);

        when(trainService.createTrain(trainRequest, "dpericles6@gmail.com"))
       .thenReturn(train);

        when(authentication.getName()).thenReturn("dpericles6@gmail.com");


        ResponseEntity<Train> created = trainController.createTrain(trainRequest, authentication);

        assertNotNull(created);
      
        assertEquals("Classic Elite Pro", created.getBody().getNameTrain());
        assertEquals("dpericles6@gmail.com", created.getBody().getAthleteId());
        assertEquals("Classic Physique", created.getBody().getCategory());
        assertEquals("Divisao semanal avan‡ada estilo Classic Physique", created.getBody().getDescription());
        assertEquals(now, created.getBody().getCreatedAt());
        assertEquals(1, train.getSchedules().size());
         assertEquals("Segunda-Feira", train.getSchedules().get(0).getWeekday());
        assertEquals("Peito e Ombros", train.getSchedules().get(0).getMusclegroup());
        assertEquals("Volume e densidade peitoral", train.getSchedules().get(0).getEmphasis());
        assertEquals(1, train.getSchedules().get(0).getExercises().size());
        assertEquals("Supino reto barra", train.getSchedules().get(0).getExercises().get(0).getNameExercise());
        assertEquals(4, train.getSchedules().get(0).getExercises().get(0).getSeries());
        assertEquals("8-10", train.getSchedules().get(0).getExercises().get(0).getRepetitions());
        assertEquals("90s", train.getSchedules().get(0).getExercises().get(0).getBreakTime());
        assertEquals("1a leve, ultimas 2 ate falha", train.getSchedules().get(0).getExercises().get(0).getObservation());

        verify(trainService).createTrain(trainRequest, "dpericles6@gmail.com");

    }

   

    @Test
    void shouldgetTrainById(){

        LocalDateTime now = LocalDateTime.now();

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino reto barra");
        exercise.setSeries(4);
        exercise.setRepetitions("8-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(List.of(exercise));

        Train train = new Train(
            "6a1b8bc47747b33af4eef96b",
            "Mens Aesthetic Flow",
            "daniel@host.com.br",
            "Mens Physique",
            "Foco em simetria, cintura fina e defini‡ao muscular", 
            now,
            List.of(trainSchedule)
        );

        
        Authentication  authentication = mock(Authentication.class);

        when(authentication.getName()).thenReturn("dpericles6@gmail.com");


        when(trainService.getTrainByAtlheteId("dpericles6@gmail.com")).thenReturn(List.of(train));
            // when(trainRepository.findAll()).thenReturn(List.of(train));

        ResponseEntity<List<Train>> result = trainController.getMyTrain(authentication);

  
        assertNotNull(result.getBody());

        List<Train> trains = result.getBody();

        assertEquals(1, trains.size());

        Train trainResult = trains.get(0);

        assertEquals("6a1b8bc47747b33af4eef96b", trainResult.getId());
        assertEquals("Mens Aesthetic Flow", trainResult.getNameTrain());
        assertEquals("daniel@host.com.br", trainResult.getAthleteId());
        assertEquals("Mens Physique", trainResult.getCategory());
        assertEquals("Foco em simetria, cintura fina e defini‡ao muscular", trainResult.getDescription());
        assertEquals(now, trainResult.getCreatedAt());
        assertEquals(1, train.getSchedules().size());
        assertEquals("Segunda-Feira", train.getSchedules().get(0).getWeekday());
        assertEquals("Peito e Ombros", train.getSchedules().get(0).getMusclegroup());
        assertEquals("Volume e densidade peitoral", train.getSchedules().get(0).getEmphasis());
        assertEquals(1, train.getSchedules().get(0).getExercises().size());
        assertEquals("Supino reto barra", train.getSchedules().get(0).getExercises().get(0).getNameExercise());
        assertEquals(4, train.getSchedules().get(0).getExercises().get(0).getSeries());
        assertEquals("8-10", train.getSchedules().get(0).getExercises().get(0).getRepetitions());
        assertEquals("90s", train.getSchedules().get(0).getExercises().get(0).getBreakTime());
        assertEquals("1a leve, ultimas 2 ate falha", train.getSchedules().get(0).getExercises().get(0).getObservation());

    }

    @Test
    void shouldpatchTrainById(){
         LocalDateTime now = LocalDateTime.now();

          ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino reto barra",
            4,
            "8-10",
            "90s",
            "1a leve, ultimas 2 ate falha"
        );

        TrainScheduleRequest trainScheduleReq  = new TrainScheduleRequest(
            "Segunda",
            "Peito e Ombros",
            "Volume e densidade peitoral",
            List.of(exerciseRequest)
        );
        
        TrainRequest trainRequest = new TrainRequest(
            "Classic Elite Pro",
            "Mens Physique",
             "Divisao semanal avan‡ada estilo Mens Physique",
             now,
             List.of(trainScheduleReq)
        );

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino reto barra");
        exercise.setSeries(4);
        exercise.setRepetitions("8-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(List.of(exercise));

        Train train = new Train(
            "1",
            "ahlete@host.com.br",
            "Classic Elite Pro",
            "Mens Physique",
            "Divisao semanal avan‡ada estilo Mens Physique", 
            now,
            List.of(trainSchedule)
        );

        when(trainService.patchTrainById("1",  trainRequest)).thenReturn(train);

        ResponseEntity<Train> result = trainController.patchTrainById("1",  trainRequest);
        assertEquals("Mens Physique", result.getBody().getCategory());
        assertEquals("Divisao semanal avan‡ada estilo Mens Physique", result.getBody().getDescription());
        
    }

    @Test
    void shouldpatchTrainScheduleById(){
      LocalDateTime now = LocalDateTime.now();

          ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino reto barra",
            4,
            "8-10",
            "90s",
            "1a leve, ultimas 2 ate falha"
        );

        TrainScheduleRequest trainScheduleReq  = new TrainScheduleRequest(
            "Segunda",
            "Peito e Ombros",
            "Volume e densidade peitoral",
            List.of(exerciseRequest)
        );
        
        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino reto barra");
        exercise.setSeries(4);
        exercise.setRepetitions("8-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Sexta-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(List.of(exercise));

        Train train = new Train(
            "1",
            "ahlete@host.com.br",
            "Classic Elite Pro",
            "Mens Physique",
            "Divisao semanal avan‡ada estilo Mens Physique", 
            now,
            List.of(trainSchedule)
        );

        when(trainScheduleExerciseService.patchTrainScheduleById("1", 0, trainScheduleReq)).thenReturn(train);
        
        ResponseEntity<Train> result = trainController.patchTrainScheduleById("1", 0, trainScheduleReq);
        assertNotNull(result);
        assertEquals("Sexta-Feira", result.getBody().getSchedules().get(0).getWeekday());

    }

    @Test
    void shouldpatchTrainExerciseById(){
      LocalDateTime now = LocalDateTime.now();

          ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino reto barra",
            4,
            "8-10",
            "90s",
            "1a leve, ultimas 2 ate falha"
        );


        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino inclinado com halteres");
        exercise.setSeries(4);
        exercise.setRepetitions("8-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Sexta-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(List.of(exercise));

        Train train = new Train(
            "1",
            "ahlete@host.com.br",
            "Classic Elite Pro",
            "Mens Physique",
            "Divisao semanal avan‡ada estilo Mens Physique", 
            now,
            List.of(trainSchedule)
        );

        when(trainScheduleExerciseService.patchTrainExercise("1", 0,  0, exerciseRequest)).thenReturn(train);
        ResponseEntity<Train> result = trainController.patchTrainExerciseById("1", 0, 0, exerciseRequest);
        
        assertNotNull(result);
        assertEquals("Supino inclinado com halteres", result.getBody().getSchedules().get(0).getExercises().get(0).getNameExercise());
    }

    @Test
    void shouldUpdateTrainById(){
           ExerciseRequest exerciseRequest = new ExerciseRequest(
            "Supino reto barra",
            4,
            "8-10",
            "90s",
            "1a leve, ultimas 2 ate falha"
        );

        TrainScheduleRequest trainScheduleReq  = new TrainScheduleRequest(
            "Segunda",
            "Peito e Ombros",
            "Volume e densidade peitoral",
            List.of(exerciseRequest)
        );

         LocalDateTime now = LocalDateTime.now();
        
        TrainRequest trainRequest = new TrainRequest(
            "Classic Elite Pro",
            "Classic Physique",
             "Divisao semanal avan‡ada estilo Classic Physique",
             now,
             List.of(trainScheduleReq)
        );

        
        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino inclinado com halteres");
        exercise.setSeries(5);
        exercise.setRepetitions("6-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("Foco na contracao maxima");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e ombros (enfase superior)");
        trainSchedule.setEmphasis("Hipertrofia e densidade");
        trainSchedule.setExercises(new ArrayList<>(List.of(exercise)));

        Train train = new Train();
        train.setId("1");
        train.setNameTrain("Open Mass Pro");
        train.setAthleteId("123");
        train.setCategory("Open Physique");
        train.setDescription("Treino de forma proxima e densidade total");
        train.setCreatedAt(now);
        train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

          when(trainService.updateTrainById("1", trainRequest))
            .thenReturn(train);

        ResponseEntity<Train> updated = trainController.updateTrainById("1", trainRequest);

        assertNotNull(updated);
        assertEquals("Open Mass Pro", updated.getBody().getNameTrain());
        assertEquals("123", updated.getBody().getAthleteId());
        assertEquals("Open Physique", updated.getBody().getCategory());
        assertEquals("Treino de forma proxima e densidade total", updated.getBody().getDescription());
        assertEquals(now, updated.getBody().getCreatedAt());
        assertEquals(1, train.getSchedules().size());
        assertEquals("Segunda-Feira", train.getSchedules().get(0).getWeekday());
        assertEquals("Peito e ombros (enfase superior)", train.getSchedules().get(0).getMusclegroup());
        assertEquals("Hipertrofia e densidade", train.getSchedules().get(0).getEmphasis());
        assertEquals(1, train.getSchedules().get(0).getExercises().size());
        assertEquals("Supino inclinado com halteres", train.getSchedules().get(0).getExercises().get(0).getNameExercise());
        assertEquals(5, train.getSchedules().get(0).getExercises().get(0).getSeries());
        assertEquals("6-10", train.getSchedules().get(0).getExercises().get(0).getRepetitions());
        assertEquals("90s", train.getSchedules().get(0).getExercises().get(0).getBreakTime());
        assertEquals("Foco na contracao maxima", train.getSchedules().get(0).getExercises().get(0).getObservation());
    }

    @Test
    void shouldDeleteTrainById(){

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino inclinado com halteres");
        exercise.setSeries(5);
        exercise.setRepetitions("6-10");
        exercise.setBreakTime("90s");
        exercise.setObservation("Foco na contracao maxima");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e ombros (enfase superior)");
        trainSchedule.setEmphasis("Hipertrofia e densidade");
        trainSchedule.setExercises(new ArrayList<>(List.of(exercise)));

        LocalDateTime now = LocalDateTime.now();
        Train train = new Train();
        train.setId("1");
        train.setNameTrain("Open Mass Pro");
        train.setAthleteId("123");
        train.setCategory("Open Physique");
        train.setDescription("Treino de forma proxima e densidade total");
        train.setCreatedAt(now);
        train.setSchedules(new ArrayList<>(List.of(trainSchedule)));
        
        when(trainService.deleteTrainById("1")).thenReturn(train);
        trainController.deleteTrainById("1");
        verify(trainService).deleteTrainById("1");
    }
}
