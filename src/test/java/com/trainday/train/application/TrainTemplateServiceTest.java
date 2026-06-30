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

import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.domain.repository.TrainRepository;
import com.trainday.train.domain.repository.TrainTemplateRepository;

@ExtendWith(MockitoExtension.class)
public class TrainTemplateServiceTest {

    @InjectMocks
    TrainTemplateService trainTemplateService;

    @Mock
    TrainTemplateRepository trainTemplateRepository;

    @Mock
    TrainRepository trainRepository;

    @Test
    void shouldApplyTemplateTrain(){
        TrainTemplate trainTemplate = new TrainTemplate();
        trainTemplate.setNameTrain("Classic Elite Pro");
        trainTemplate.setCategory("Classic Physique");
        trainTemplate.setDescription("Divisão semanal avançada estilo Classic Physique");
        trainTemplate.setCreatedAt(LocalDateTime.now());
          trainTemplate.setSchedules(List.of(new TrainSchedule(
        "Segunda",
        "Peito e Ombros",
        "Volume e densidade",
            List.of(new Exercise(
                "Supino Reto",
                4,
                "10-12",
                "60",
                "Use barra de 20kg"
            ))
        )));

        when(trainTemplateRepository.findById("1")).thenReturn(Optional.of(trainTemplate));
        when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

    Train result = trainTemplateService.applyTemplateTrain("1", "athlete123");

    assertNotNull(result);
    assertEquals("Classic Elite Pro", result.getNameTrain());
    assertEquals("athlete123", result.getAthleteId());
    assertEquals("Classic Physique", result.getCategory());
    assertEquals("Divisão semanal avançada estilo Classic Physique", result.getDescription());
    assertEquals(1, result.getSchedules().size());
    assertEquals("Segunda", result.getSchedules().get(0).getWeekday());
    }


    @Test
    void ShouldGetTrainsList(){


         TrainTemplate train = new TrainTemplate(
        "1",
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
  
    when(trainTemplateRepository.findAll()).thenReturn(List.of(train));

    List<TrainTemplate> result = trainTemplateService.getTrainTemplate();

    assertNotNull(result);
    assertEquals(1, result.size());
    assertEquals("Treino A", result.get(0).getNameTrain());
    assertEquals("Força", result.get(0).getCategory());
    assertEquals("Treino focado em força para membros superiores", result.get(0).getDescription());
    assertEquals(1, result.get(0).getSchedules().size());
    



    }



}
