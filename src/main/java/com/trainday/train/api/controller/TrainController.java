package com.trainday.train.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.application.TrainScheduleExerciseService;
import com.trainday.train.application.TrainService;
import com.trainday.train.domain.models.Train;
import com.trainday.train.infra.service.JwtService;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;
    private static final Logger log = LoggerFactory.getLogger(TrainService.class);
    private final TrainScheduleExerciseService trainScheduleExerciseService;

    public TrainController(
            TrainService trainService,
            TrainScheduleExerciseService trainScheduleExerciseService) {
        this.trainService = trainService;
        this.trainScheduleExerciseService = trainScheduleExerciseService;

    }

    @PostMapping("/{cref}")
    public ResponseEntity<Train> createTrain(
            @RequestBody TrainRequest req,
            Authentication authentication) {

        log.info("Received request to create train: {}", req);
        String athleteId = authentication.getName();
        Train createdTrain = trainService.createTrain(req, athleteId);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdTrain);
    }

    @GetMapping("/athlete/train/{cpf}")
    public ResponseEntity<List<Train>> getTrainByAthlete(
            @PathVariable String cpf) {
        return ResponseEntity.ok(trainService.getTrainByCpf(cpf));
    }

    @GetMapping("/athlete/MyTrain/{cpf}")
    public ResponseEntity<List<Train>> getTrainByCpf(
            @PathVariable String cpf) {
        return ResponseEntity.ok(trainService.getTrainByCpf(cpf));
    }

    @PatchMapping("/AthletesTrain/{cpf}")
    public ResponseEntity<Train> patchTrainByCpf(@PathVariable String cpf, @RequestBody TrainRequest req) {
        return ResponseEntity.ok(trainService.patchTrainByCpf(cpf, req));
    }

    @PatchMapping("/AthletesTrain/{cpf}/schedule/{index}")
    public ResponseEntity<Train> patchTrainScheduleByCpf(
            @PathVariable String cpf,
            @PathVariable int index,
            @RequestBody TrainScheduleRequest req) {
        Train train = trainScheduleExerciseService.patchTrainScheduleById(cpf, index, req);

        return ResponseEntity.ok(train);
    }

    @PatchMapping("/AthletesTrain/{cpf}/schedule/{scheduleIndex}/exercise/{exerciseIndex}")
    public ResponseEntity<Train> patchTrainExerciseByCpf(
            @PathVariable String cpf,
            @PathVariable int scheduleIndex,
            @PathVariable int exerciseIndex,
            @RequestBody ExerciseRequest req) {

        Train train = trainScheduleExerciseService.patchTrainExercise(cpf, scheduleIndex, exerciseIndex, req);
        return ResponseEntity.ok(train);
    }

    @PutMapping("/AthletesTrain/{cpf}")
    public ResponseEntity<Train> updateTrainById(@PathVariable String cpf, @RequestBody TrainRequest updateTrainReq) {
        return ResponseEntity.ok(trainService.updateTrainByCpf(cpf, updateTrainReq));
    }

    @DeleteMapping("/AthletesTrain/{cpf}")
    public ResponseEntity<Void> deleteTrainById(@PathVariable String cpf) {
        trainService.deleteTrainByCpf(cpf);
        return ResponseEntity.noContent().build();
    }

}
