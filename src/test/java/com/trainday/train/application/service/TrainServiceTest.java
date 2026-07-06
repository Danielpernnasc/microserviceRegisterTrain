package com.trainday.train.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;
import com.trainday.train.domain.repository.TrainRepository;
import com.trainday.train.infra.DTO.response.AthleteClientResponse;
import com.trainday.train.infra.client.AthleteClient;

@ExtendWith(MockitoExtension.class)
public class TrainServiceTest {

        @InjectMocks
        TrainService trainService;

        @Mock
        TrainRepository trainRepository;

        @Mock
        RepositoryPhyEdProf repositoryepe;

        @Mock
        AthleteClient athleteClient;

        LocalDateTime now = LocalDateTime.now();

        @Test
        void shouldCreateTrain() {

                TrainRequest req = new TrainRequest(
                                "athlete@host.com.br",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP",
                                "Classic Elite Pro",
                                "Classic Physique",
                                "Divisao semanal avan‡ada estilo Classic Physique",
                                now,
                                List.of(new TrainScheduleRequest(
                                                "Segunda-feira",
                                                "Peito",
                                                "Força",
                                                List.of(new ExerciseRequest(
                                                                "Supino Reto",
                                                                4,
                                                                "10-12",
                                                                "60",
                                                                "Use uma barra de 20kg e aumente o peso progressivamente")))));

                when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));
                when(athleteClient.findByCpf("999.999.999-99"))
                                .thenReturn(new AthleteClientResponse(
                                                "athlete-id-123",
                                                "999.999.999-99",
                                                "Athlete Name",
                                                "Athlete Social Name",
                                                "athlete@host.com.br",
                                                "1999-01-01",
                                                "M",
                                                "1234567",
                                                "1.80",
                                                "80",
                                                Role.ATHLETE));
                when(repositoryepe.findByEmail("athlete@host.com.br"))
                                .thenReturn(Optional.of(new PhysicalEducationProfessional(
                                                "CREF123456-G-SP",
                                                "John Doe",
                                                "professional@host.com.br",
                                                LocalDate.of(1990, 1, 1),
                                                "123.456.789-00",
                                                "(11) 98765-4321",
                                                "123 Main St, City, Country",
                                                "Cref-123456-G-SP",
                                                Role.PERSONAL_TRAINER)));
                when(repositoryepe.findByCref("athlete@host.com.br")).thenReturn(Optional.empty());

                Train create = trainService.createTrain(req, "athlete@host.com.br");

                assertNotNull(create);
                assertEquals("Classic Elite Pro", create.getNameTrain());
                assertEquals("athlete-id-123", create.getAthleteId());
                assertEquals("Classic Physique", create.getCategory());
                assertEquals("Divisao semanal avan‡ada estilo Classic Physique", create.getDescription());
                assertEquals("athlete@host.com.br", create.getAthleteemail());
                assertEquals(LocalDateTime.now().getDayOfYear(), create.getCreatedAt().getDayOfYear());
                assertEquals(1, create.getSchedules().size());
                assertEquals("Segunda-feira", create.getSchedules().get(0).getWeekday());
                assertEquals("Peito", create.getSchedules().get(0).getMusclegroup());
                assertEquals("Força", create.getSchedules().get(0).getEmphasis());
                assertEquals(1, create.getSchedules().get(0).getExercises().size());
                assertEquals("Supino Reto", create.getSchedules().get(0).getExercises().get(0).getNameExercise());
                assertEquals(4, create.getSchedules().get(0).getExercises().get(0).getSeries());
                assertEquals("10-12", create.getSchedules().get(0).getExercises().get(0).getRepetitions());
                assertEquals("60", create.getSchedules().get(0).getExercises().get(0).getBreakTime());
                assertEquals("Use uma barra de 20kg e aumente o peso progressivamente",
                                create.getSchedules().get(0).getExercises().get(0).getObservation());

        }

        @Test
        void shouldgetTrainforId() {
                LocalDateTime now = LocalDateTime.now();
                Train train = new Train();
                train.setId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setAthleteName("Daniel");
                train.setAthleteemail("athlete@host.com.br");
                train.setRoleAthlete(Role.ATHLETE);
                train.setNameTrain("Mens Aesthetic Flow");
                train.setAthleteId("daniel@host.com.br");
                train.setCategory("Mens Physique");
                train.setDescription("Foco em simetria, cintura fina e defini‡ao muscular");
                train.setCreatedAt(now);
                train.setSchedules(List.of(new TrainSchedule(
                                "Segunda-feira",
                                "Peito",
                                "Força",
                                List.of(new Exercise(
                                                "Supino Reto",
                                                4,
                                                "10-12",
                                                "60",
                                                "Use uma barra de 20kg e aumente o peso progressivamente")))));

                when(trainRepository.findByAthleteCpf("999.999.999-99")).thenReturn(List.of(train));

                List<Train> found = trainService.getTrainByCpf("999.999.999-99");
                assertNotNull(found);
                assertEquals(1, found.size());
                Train trainFound = found.get(0);

                assertEquals("Mens Aesthetic Flow", trainFound.getNameTrain());
                assertEquals("daniel@host.com.br", trainFound.getAthleteId());
                assertEquals("Mens Physique", trainFound.getCategory());
                assertEquals("Foco em simetria, cintura fina e defini‡ao muscular", trainFound.getDescription());
                assertEquals(LocalDateTime.now().getDayOfYear(), trainFound.getCreatedAt().getDayOfYear());
                assertEquals(1, trainFound.getSchedules().size());
                assertEquals("Segunda-feira", trainFound.getSchedules().get(0).getWeekday());
                assertEquals("Peito", trainFound.getSchedules().get(0).getMusclegroup());
                assertEquals("Força", trainFound.getSchedules().get(0).getEmphasis());
                assertEquals(1, trainFound.getSchedules().get(0).getExercises().size());
                assertEquals("Supino Reto", trainFound.getSchedules().get(0).getExercises().get(0).getNameExercise());
                assertEquals(4, trainFound.getSchedules().get(0).getExercises().get(0).getSeries());
                assertEquals("10-12", trainFound.getSchedules().get(0).getExercises().get(0).getRepetitions());
                assertEquals("60", trainFound.getSchedules().get(0).getExercises().get(0).getBreakTime());
                assertEquals("Use uma barra de 20kg e aumente o peso progressivamente",
                                trainFound.getSchedules().get(0).getExercises().get(0).getObservation());

        }

        @Test
        void shouldPatchTrainById() {
                Train train = new Train();
                train.setId("123Train");
                train.setAthleteCpf("999.999.999-99");
                train.setAthleteName("Daniel");
                train.setAthleteemail("athlete@host.com.br");
                train.setRoleAthlete(Role.ATHLETE);
                train.setNameTrain("Mens Aesthetic Flow");
                train.setAthleteId("daniel@host.com.br");
                train.setCategory("Mens Physique");
                train.setDescription("Foco em simetria, cintura fina e defini‡ao muscular");
                train.setCreatedAt(now);
                train.setSchedules(List.of(new TrainSchedule(
                                "Segunda-feira",
                                "Peito",
                                "Força",
                                List.of(new Exercise(
                                                "Supino Reto",
                                                4,
                                                "10-12",
                                                "60",
                                                "Use uma barra de 20kg e aumente o peso progressivamente")))));

                TrainRequest patchReq = new TrainRequest(
                                null,
                                null,
                                null,
                                null,
                                "Open Mass Pro", // só isso muda
                                null,
                                null,
                                null,
                                null);

                when(trainRepository.findByAthleteCpf("999.999.999-99")).thenReturn(List.of(train));
                when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

                Train result = trainService.patchTrainByCpf("999.999.999-99", patchReq);

                assertNotNull(result);
                assertEquals("Open Mass Pro", result.getNameTrain()); // mudou
                assertEquals("daniel@host.com.br", result.getAthleteId()); // manteve
                assertEquals("Mens Physique", result.getCategory());
                assertEquals("Foco em simetria, cintura fina e defini‡ao muscular", result.getDescription()); // manteve
                assertEquals(1, result.getSchedules().size()); // manteve

        }

        @Test
        void shouldPatchTrainByIdWithAllFields() {
                Train existingTrain = new Train();
                existingTrain.setId("1");
                existingTrain.setAthleteCpf("999.999.999-99");
                existingTrain.setAthleteId("athlete123");
                existingTrain.setNameTrain("Treino A");
                existingTrain.setCategory("Força");
                existingTrain.setDescription("Descrição antiga");
                existingTrain.setCreatedAt(LocalDateTime.now());
                existingTrain.setSchedules(List.of());

                when(trainRepository.findByAthleteCpf("999.999.999-99")).thenReturn(List.of(existingTrain));
                when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));

                TrainScheduleRequest trainScheduleReq = new TrainScheduleRequest(
                                "Terça-feira",
                                "Perna",
                                "Hipertrofia",
                                List.of(new ExerciseRequest(
                                                "Agachamento Livre",
                                                4,
                                                "8-10",
                                                "90",
                                                "Mantenha a postura correta e aumente o peso progressivamente")));
                // Agora passa todos os campos preenchidos
                TrainRequest patchReq = new TrainRequest(
                                "999.999.999-99",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP",
                                "Classic Elite Pro",
                                "Classic Physique",
                                "Divisao semanal avan‡ada estilo Classic Physique",
                                now,
                                List.of(trainScheduleReq));

                Train result = trainService.patchTrainByCpf("999.999.999-99", patchReq);

                assertNotNull(result);
                assertEquals("Classic Elite Pro", result.getNameTrain());
                assertEquals("Classic Physique", result.getCategory());
                assertEquals("Divisao semanal avan‡ada estilo Classic Physique", result.getDescription());
                assertEquals(1, result.getSchedules().size());
                assertEquals("Terça-feira", result.getSchedules().get(0).getWeekday());
                assertEquals("Perna", result.getSchedules().get(0).getMusclegroup());

        }

        @Test
        void shouldUpdateTrain() {
                Train existtrain = new Train();

                existtrain.setId("999.999.999-99");
                existtrain.setAthleteId("999.999.999-99");
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
                                                "Use uma barra de 20kg e aumente o peso progressivamente")))));

                when(trainRepository.findByAthleteCpf("999.999.999-99")).thenReturn(List.of(existtrain));
                when(trainRepository.save(any(Train.class))).thenAnswer(invocation -> invocation.getArgument(0));
                when(repositoryepe.findByCref("CREF123456-G-SP"))
                                .thenReturn(Optional.of(new PhysicalEducationProfessional(
                                                "CREF123456-G-SP",
                                                "John Doe",
                                                "professional@host.com.br",
                                                LocalDate.of(1990, 1, 1),
                                                "123.456.789-00",
                                                "(11) 98765-4321",
                                                "123 Main St, City, Country",
                                                "CREF123456-G-SP",
                                                Role.PERSONAL_TRAINER)));

                Train result = trainService.updateTrainByCpf("999.999.999-99", new TrainRequest("999.999.999-99",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP", "Treino B",
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
                                                                "Mantenha a postura correta e aumente  o peso progressivamente"))))));

                assertNotNull(result);
                assertEquals("Treino B", result.getNameTrain());
                assertEquals("999.999.999-99", result.getAthleteId());
                assertEquals("Hipertrofia", result.getCategory());
                assertEquals("Treino focado em hipertrofia para membros inferiores", result.getDescription());
                assertEquals(LocalDateTime.now().getDayOfYear(), result.getCreatedAt().getDayOfYear());
                assertEquals(1, result.getSchedules().size());
                assertEquals("Terça-feira", result.getSchedules().get(0).getWeekday());
                assertEquals("Perna", result.getSchedules().get(0).getMusclegroup());
                assertEquals("Hipertrofia", result.getSchedules().get(0).getEmphasis());
                assertEquals(1, result.getSchedules().get(0).getExercises().size());
                assertEquals("Agachamento Livre", result.getSchedules().get(0).getExercises().get(0).getNameExercise());
                assertEquals(4, result.getSchedules().get(0).getExercises().get(0).getSeries());
                assertEquals("8-10", result.getSchedules().get(0).getExercises().get(0).getRepetitions());
                assertEquals("90", result.getSchedules().get(0).getExercises().get(0).getBreakTime());
                assertEquals("Mantenha a postura correta e aumente  o peso progressivamente",
                                result.getSchedules().get(0).getExercises().get(0).getObservation());

        }

        @Test
        void shouldDeleteTrain() {
                Train existtrain = new Train();

                existtrain.setId("999.999.999-99");
                existtrain.setAthleteId("999.999.999-99");
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
                                                "Use uma barra de 20kg e aumente o peso progressivamente")))));

                when(trainRepository.findByAthleteCpf("999.999.999-99")).thenReturn(List.of(existtrain));

                doNothing().when(trainRepository).delete(any(Train.class));

                assertDoesNotThrow(() -> trainService.deleteTrainByCpf("999.999.999-99"));

                verify(trainRepository).delete(any(Train.class));

        }

}
