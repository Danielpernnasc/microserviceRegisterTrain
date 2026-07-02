package com.trainday.train.api.DTO.request;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;

import com.trainday.train.domain.models.enums.Role;

public class TrainRequestTest {

    @Test
    void shouldTrainRequest() {

        ExerciseRequest exerciseRequest = new ExerciseRequest(
                "Supino Reto",
                4,
                "8-12",
                "60s",
                "Foco na contração peitoral");

        TrainScheduleRequest trainScheduleRequest = new TrainScheduleRequest(
                "Segunda-Feira",
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
                List.of(trainScheduleRequest));

        assertNotNull(trainRequest);
        assertNotNull(trainRequest.schedules());
        assertNotNull(trainRequest.schedules().get(0).exercises());

    }

}
