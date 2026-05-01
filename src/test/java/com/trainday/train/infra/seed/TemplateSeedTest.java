package com.trainday.train.infra.seed;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.domain.repository.TrainTemplateRepository;

@ExtendWith(MockitoExtension.class)
public class TemplateSeedTest {

    @Mock
    private TrainTemplateRepository trainTemplateRepository;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private TemplateSeed templateSeed;

    @Test
    void shouldTemplateSeedWhenRepositoryEmpty() throws Exception{

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
        trainSchedule.setExercise(List.of(exercise));

        TrainTemplate trainTemplate = new TrainTemplate();
        trainTemplate.setId("1");
        trainTemplate.setNameTrain("Classic Elite Pro");
        trainTemplate.setCategory("Classic Phisique");
        trainTemplate.setDescription("Divisao semanal avan‡ada estilo Classic Physique.");
        trainTemplate.setCreatedAt(LocalDateTime.now());
        trainTemplate.setSchedules(List.of(trainSchedule));

        trainTemplate.setId("2");
        trainTemplate.setNameTrain("Mens Aesthetic Flow");
        trainTemplate.setCategory("Mens Physique");
        trainTemplate.setDescription("Foco em simetria, cintura fina e defini‡ao muscular.");
        trainTemplate.setCreatedAt(LocalDateTime.now());
        trainTemplate.setSchedules(List.of(trainSchedule));

        trainTemplate.setId("3");
        trainTemplate.setNameTrain("Natural Strength Plan");
        trainTemplate.setCategory("Natural Physique");
        trainTemplate.setDescription("Treino focado em hipertrofia, densidade muscular e recuperação natural.");
        trainTemplate.setCreatedAt(LocalDateTime.now());
        trainTemplate.setSchedules(List.of(trainSchedule));

        trainTemplate.setId("4");
        trainTemplate.setNameTrain("Open Mass Pro");
        trainTemplate.setCategory("Open Physique");
        trainTemplate.setDescription("Treino de forma proxima e densidade total");
        trainTemplate.setCreatedAt(LocalDateTime.now());
        trainTemplate.setSchedules(List.of(trainSchedule));

        when(trainTemplateRepository.count()).thenReturn(0L);
        try {
            when(objectMapper.readValue(any(InputStream.class), eq(TrainTemplate.class)))
                .thenReturn(trainTemplate);
        } catch (IOException e) {
            e.printStackTrace();
        }

        templateSeed.run();

            verify(trainTemplateRepository, times(4)).save(any(TrainTemplate.class));
    
    }

    @Test
    void shouldsaveFileNotFind() throws Exception{
        assertThrows(RuntimeException.class, () -> {
            templateSeed.saveFile("trains/arquivo-inexistente.json");
        });
    }

}
