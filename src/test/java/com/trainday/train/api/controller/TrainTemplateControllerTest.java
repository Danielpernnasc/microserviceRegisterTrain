package com.trainday.train.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.application.TrainTemplateService;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.infra.security.JwtService;

@ExtendWith(MockitoExtension.class)
public class TrainTemplateControllerTest {

    @Mock
    TrainTemplateService trainTemplateService;

    @Mock
    JwtService jwtService;

 
    @InjectMocks
    TrainTemplateController trainTemplateController;

    @Test
    void shouldapplyTemplateTrain(){

        Exercise exercise = new Exercise();
        exercise.setNameExercise("Supino reto barra");
        exercise.setSeries(4);
        exercise.setBreakTime("90s");
        exercise.setObservation( "1a leve, ultimas 2 ate falha");

        TrainSchedule trainSchedule = new TrainSchedule();
        trainSchedule.setWeekday("Segunda-Feira");
        trainSchedule.setMusclegroup("Peito e Ombros");
        trainSchedule.setEmphasis("Volume e densidade peitoral");
        trainSchedule.setExercises(List.of(exercise));

   
        TrainTemplate trainTemplate = new TrainTemplate();
        trainTemplate.setId("1");
        trainTemplate.setNameTrain("Classic Elite Pro");
        trainTemplate.setCategory("Classic Physique");
        trainTemplate.setDescription("Divisao semanal avan‡ada estilo Classic Physique");
        trainTemplate.setCreatedAt(LocalDateTime.now());
        trainTemplate.setSchedules(List.of(trainSchedule));

        LocalDateTime now = LocalDateTime.now();

        Train train = new Train();
        train.setId("1");
        train.setAthleteId("123");
        train.setNameTrain("Classic Elite Pro");
        train.setCategory("Classic Physique");
        train.setDescription("Divisao semanal avançada estilo Classic Physique");
        train.setCreatedAt(now);
        train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

        when(trainTemplateService.applyTemplateTrain("1", "123")).thenReturn(train);
        when(jwtService.extractEmail(any())).thenReturn("123");
        
        ResponseEntity<Train> result = trainTemplateController.applyTemplateTrain("1", "Bearer fake-token");

        assertNotNull(result);
        assertEquals(HttpStatus.CREATED, result.getStatusCode());
        assertEquals("Classic Elite Pro", result.getBody().getNameTrain());
        
    }

    @Test
    void shouldgetTrainTemplate(){
        
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

        TrainTemplate train = new TrainTemplate(
            "ahlete@host.com.br",
            "Classic Elite Pro",
            "Classic Physique",
            "Divisao semanal avan‡ada estilo Classic Physique", 
            now,
            List.of(trainSchedule)
   
        );
        List<TrainTemplate> trains = List.of(train);

       
        when(trainTemplateService.getTrainTemplate()).thenReturn(trains);

        ResponseEntity<List<TrainTemplate>> result = trainTemplateController.getTrainTemplate();

        assertEquals(1, result.getBody().size());
        assertEquals("Classic Elite Pro", result.getBody().get(0).getNameTrain());
        assertEquals("Classic Physique", result.getBody().get(0).getCategory());
        assertEquals("Divisao semanal avan‡ada estilo Classic Physique", result.getBody().get(0).getDescription());
        assertEquals(now, result.getBody().get(0).getCreatedAt());
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






}
