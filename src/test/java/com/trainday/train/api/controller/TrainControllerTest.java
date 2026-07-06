package com.trainday.train.api.controller;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.infra.service.JwtService;
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
import com.trainday.train.application.service.TrainScheduleExerciseService;
import com.trainday.train.application.service.TrainService;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;

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
        void ShouldCreateTrain() {

                ExerciseRequest exerciseRequest = new ExerciseRequest(
                                "Supino reto barra",
                                4,
                                "8-10",
                                "90s",
                                "1a leve, ultimas 2 ate falha");

                TrainScheduleRequest trainScheduleReq = new TrainScheduleRequest(
                                "Segunda",
                                "Peito e Ombros",
                                "Volume e densidade peitoral",
                                List.of(exerciseRequest));

                LocalDateTime now = LocalDateTime.now();

                TrainRequest trainRequest = new TrainRequest(
                                "999.999.999-99",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP",
                                "Classic Elite Pro",
                                "Classic Physique",
                                "Divisao semanal avan‡ada estilo Classic Physique",
                                now,
                                List.of(trainScheduleReq));

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
                train.setAthleteId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setAthleteName("Daniel Péricles do Nascimento");
                train.setAthleteemail("athlete@host.com");
                train.setCref("CREF123456-G-SP");
                train.setRoleAthlete(Role.ATHLETE);
                train.setNameTrain("Classic Elite Pro");
                train.setAthleteId("999.999.999-99");
                train.setCategory("Classic Physique");
                train.setDescription("Divisao semanal avan‡ada estilo Classic Physique");
                train.setCreatedAt(now);
                train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

                Authentication authentication = mock(Authentication.class);

                when(trainService.createTrain(trainRequest, "999.999.999-99"))
                                .thenReturn(train);

                when(authentication.getName()).thenReturn("999.999.999-99");

                ResponseEntity<Train> created = trainController.createTrain(trainRequest, authentication);

                assertNotNull(created);
                assertNotNull(created.getBody());
                assertEquals("999.999.999-99", created.getBody().getAthleteId());
                assertEquals("999.999.999-99", created.getBody().getAthleteCpf());
                assertEquals(Role.ATHLETE, created.getBody().getRoleAthlete());
                assertEquals("CREF123456-G-SP", created.getBody().getCref());
                assertEquals("Classic Elite Pro", created.getBody().getNameTrain());
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
                assertEquals("1a leve, ultimas 2 ate falha",
                                train.getSchedules().get(0).getExercises().get(0).getObservation());

                verify(trainService).createTrain(trainRequest, "999.999.999-99");

        }

        @Test
        void shouldgetTrainById() {

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

                Train train = new Train();
                train.setId("6a1b8bc47747b33af4eef96b");
                train.setAthleteId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setAthleteName("Daniel Péricles do Nascimento");
                train.setAthleteemail("athlete@host.com");
                train.setRoleAthlete(Role.ATHLETE);
                train.setNameTrain("Classic Elite Pro");
                train.setCategory("Classic Physique");
                train.setRoleprofessional(Role.PERSONAL_TRAINER);
                train.setProfessionalId("CREF123456-G-SP");
                train.setNameProfessional("Alessandra Liz Sabrina Assunção");
                train.setCref("CREF123456-G-SP");
                train.setDescription("Divisao semanal avan‡ada estilo Classic Physique");
                train.setCreatedAt(now);
                train.setSchedules(List.of(trainSchedule));

                when(trainService.getTrainByCpf("999.999.999-99")).thenReturn(List.of(train));
                // when(trainRepository.findAll()).thenReturn(List.of(train));

                ResponseEntity<List<Train>> result = trainController.getTrainByCpf("999.999.999-99");

                assertNotNull(result.getBody());

                List<Train> trains = result.getBody();

                assertEquals(1, trains.size());

                Train trainResult = trains.get(0);

                assertEquals("6a1b8bc47747b33af4eef96b", trainResult.getId());
                assertEquals("999.999.999-99", train.getAthleteId());
                assertEquals("999.999.999-99", train.getAthleteCpf());
                assertEquals("athlete@host.com", trainResult.getAthleteemail());
                assertEquals("Classic Elite Pro", trainResult.getNameTrain());
                assertEquals("Classic Physique", trainResult.getCategory());
                assertEquals("Divisao semanal avan‡ada estilo Classic Physique", trainResult.getDescription());
                assertEquals(now, trainResult.getCreatedAt());

                assertEquals("Segunda-Feira", train.getSchedules().get(0).getWeekday());
                assertEquals("Peito e Ombros", train.getSchedules().get(0).getMusclegroup());
                assertEquals("Volume e densidade peitoral", train.getSchedules().get(0).getEmphasis());
                assertEquals(1, train.getSchedules().get(0).getExercises().size());
                assertEquals("Supino reto barra", train.getSchedules().get(0).getExercises().get(0).getNameExercise());
                assertEquals(4, train.getSchedules().get(0).getExercises().get(0).getSeries());
                assertEquals("8-10", train.getSchedules().get(0).getExercises().get(0).getRepetitions());
                assertEquals("90s", train.getSchedules().get(0).getExercises().get(0).getBreakTime());
                assertEquals("1a leve, ultimas 2 ate falha",
                                train.getSchedules().get(0).getExercises().get(0).getObservation());

        }

        @Test
        void shouldpatchTrainById() {
                LocalDateTime now = LocalDateTime.now();

                ExerciseRequest exerciseRequest = new ExerciseRequest(
                                "Supino reto barra",
                                4,
                                "8-10",
                                "90s",
                                "1a leve, ultimas 2 ate falha");

                TrainScheduleRequest trainScheduleReq = new TrainScheduleRequest(
                                "Segunda",
                                "Peito e Ombros",
                                "Volume e densidade peitoral",
                                List.of(exerciseRequest));

                TrainRequest trainRequest = new TrainRequest(
                                "999.999.999-99",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP",
                                "Classic Elite Pro",
                                "Mens Physique",
                                "Divisao semanal avan‡ada estilo Mens Physique",
                                now,
                                List.of(trainScheduleReq));

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

                Train train = new Train();
                train.setAthleteId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setRoleAthlete(Role.ATHLETE);
                train.setCref("CREF123456-G-SP");
                train.setNameTrain("Classic Elite Pro");
                train.setCategory("Mens Physique");
                train.setDescription("Divisao semanal avan‡ada estilo Mens Physique");
                train.setCreatedAt(now);
                train.setSchedules(List.of(trainSchedule));

                when(trainService.patchTrainByCpf("999.999.999-99", trainRequest)).thenReturn(train);
                ResponseEntity<Train> result = trainController.patchTrainByCpf("999.999.999-99", trainRequest);
                assertEquals("Mens Physique", result.getBody().getCategory());
                assertEquals("Divisao semanal avan‡ada estilo Mens Physique", result.getBody().getDescription());

        }

        @Test
        void shouldpatchTrainScheduleById() {
                LocalDateTime now = LocalDateTime.now();

                ExerciseRequest exerciseRequest = new ExerciseRequest(
                                "Supino reto barra",
                                4,
                                "8-10",
                                "90s",
                                "1a leve, ultimas 2 ate falha");

                TrainScheduleRequest trainScheduleReq = new TrainScheduleRequest(
                                "Segunda",
                                "Peito e Ombros",
                                "Volume e densidade peitoral",
                                List.of(exerciseRequest));

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

                Train train = new Train();
                train.setAthleteId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setRoleAthlete(Role.ATHLETE);
                train.setCref("CREF123456-G-SP");
                train.setNameTrain("Classic Elite Pro");
                train.setCategory("Mens Physique");
                train.setDescription("Divisao semanal avan‡ada estilo Mens Physique");
                train.setCreatedAt(now);
                train.setSchedules(List.of(trainSchedule));

                when(trainScheduleExerciseService.patchTrainScheduleByCpf("999.999.999-99", 0, trainScheduleReq))
                                .thenReturn(train);

                ResponseEntity<Train> result = trainController.patchTrainScheduleByCpf("999.999.999-99", 0,
                                trainScheduleReq);
                assertNotNull(result);
                assertEquals("Sexta-Feira", result.getBody().getSchedules().get(0).getWeekday());

        }

        @Test
        void shouldpatchTrainExerciseById() {
                LocalDateTime now = LocalDateTime.now();

                ExerciseRequest exerciseRequest = new ExerciseRequest(
                                "Supino reto barra",
                                4,
                                "8-10",
                                "90s",
                                "1a leve, ultimas 2 ate falha");

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

                Train train = new Train();
                train.setId("1");
                train.setAthleteId("ahlete@host.com.br");
                train.setNameTrain("Classic Elite Pro");
                train.setCategory("Mens Physique");
                train.setDescription("Divisao semanal avan‡ada estilo Mens Physique");
                train.setCreatedAt(now);
                train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

                when(trainScheduleExerciseService.patchTrainExercise("1", 0, 0, exerciseRequest)).thenReturn(train);
                ResponseEntity<Train> result = trainController.patchTrainExerciseByCpf("1", 0, 0, exerciseRequest);

                assertNotNull(result);
                assertEquals("Supino inclinado com halteres",
                                result.getBody().getSchedules().get(0).getExercises().get(0).getNameExercise());
        }

        @Test
        void shouldUpdateTrainById() {
                ExerciseRequest exerciseRequest = new ExerciseRequest(
                                "Supino reto barra",
                                4,
                                "8-10",
                                "90s",
                                "1a leve, ultimas 2 ate falha");

                TrainScheduleRequest trainScheduleReq = new TrainScheduleRequest(
                                "Segunda",
                                "Peito e Ombros",
                                "Volume e densidade peitoral",
                                List.of(exerciseRequest));

                LocalDateTime now = LocalDateTime.now();

                TrainRequest trainRequest = new TrainRequest(
                                "999.999.999-99",
                                "999.999.999-99",
                                Role.ATHLETE,
                                "CREF123456-G-SP",
                                "Classic Elite Pro",
                                "Classic Physique",
                                "Divisao semanal avan‡ada estilo Classic Physique",
                                now,
                                List.of(trainScheduleReq));

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

                when(trainService.updateTrainByCpf("1", trainRequest))
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
                assertEquals("Supino inclinado com halteres",
                                train.getSchedules().get(0).getExercises().get(0).getNameExercise());
                assertEquals(5, train.getSchedules().get(0).getExercises().get(0).getSeries());
                assertEquals("6-10", train.getSchedules().get(0).getExercises().get(0).getRepetitions());
                assertEquals("90s", train.getSchedules().get(0).getExercises().get(0).getBreakTime());
                assertEquals("Foco na contracao maxima",
                                train.getSchedules().get(0).getExercises().get(0).getObservation());
        }

        @Test
        void shouldDeleteTrainById() {

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
                train.setId("999.999.999-99");
                train.setAthleteCpf("999.999.999-99");
                train.setRoleAthlete(Role.ATHLETE);
                train.setNameTrain("Open Mass Pro");
                train.setAthleteId("123");
                train.setCategory("Open Physique");
                train.setDescription("Treino de forma proxima e densidade total");
                train.setCreatedAt(now);
                train.setSchedules(new ArrayList<>(List.of(trainSchedule)));

                when(trainService.deleteTrainByCpf("999.999.999-99")).thenReturn(train);
                trainController.deleteTrainById("999.999.999-99");
                verify(trainService).deleteTrainByCpf("999.999.999-99");
        }
}
