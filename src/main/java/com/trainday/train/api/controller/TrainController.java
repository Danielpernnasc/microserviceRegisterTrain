package com.trainday.train.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.application.TrainScheduleExerciseService;
import com.trainday.train.application.TrainService;
import com.trainday.train.domain.models.Train;
import com.trainday.train.infra.security.JwtService;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@RestController
@RequestMapping("/train")
public class TrainController {

    private final TrainService trainService;
    private static final Logger log = LoggerFactory.getLogger(TrainService.class);
    private final JwtService jwtService;
    private final TrainScheduleExerciseService trainScheduleExerciseService;

    public TrainController(
        TrainService trainService,
        JwtService jwtService,
        TrainScheduleExerciseService trainScheduleExerciseService
    ) {
        this.trainService = trainService;
        this.jwtService = jwtService;
        this.trainScheduleExerciseService = trainScheduleExerciseService;

    }

    @PostMapping
    public ResponseEntity<Train> createTrain(
        @RequestBody TrainRequest req,
        @RequestHeader("Authorization") String authHeader) {
        log.info("Received request to create train: {}", req);
        String token = authHeader.substring(7); // remove "Bearer "
        String atletaId = jwtService.extractEmail(token); // pega o id do atl
        Train createdTrain = trainService.createTrain(req, atletaId);
        return ResponseEntity.status(HttpStatus.CREATED)
            .body(createdTrain);
    }

    @GetMapping("/my-trains")
    public ResponseEntity<List<Train>> listTrains() {
        return ResponseEntity.ok(trainService.listTrains());
    }

    @GetMapping("/my-trains/{id}")
    public ResponseEntity<Train> getTrainById(@PathVariable String id) {
        return ResponseEntity.ok(trainService.getTrainById(id));
    }

    @PatchMapping("/my-trains/{id}")
    public ResponseEntity<Train> patchTrainById(@PathVariable String id, @RequestBody TrainRequest req) {
        return ResponseEntity.ok(trainService.patchTrainById(id, req));
    }

    @PatchMapping("/my-trains/{id}/schedule/{index}")
    public ResponseEntity<Train> patchTrainScheduleById(
        @PathVariable String id,
        @PathVariable int index,
         @RequestBody TrainScheduleRequest req) {
                System.out.println("ENTROU PATCH SCHEDULE"); 
        //Train train = trainService.patchTrainScheduleById(id, index, req);
        Train train = trainScheduleExerciseService.patchTrainScheduleById(id, index, req);

        return ResponseEntity.ok(train);
    }

    @PatchMapping("/my-trains/{id}/schedule/{scheduleIndex}/exercise/{exerciseIndex}")
    public ResponseEntity<Train> patchTrainExerciseById(
        @PathVariable String id,
        @PathVariable int scheduleIndex,
        @PathVariable int exerciseIndex,
        @RequestBody ExerciseRequest req) {
            System.out.println("ENTROU PATCH EXERCISE");

       // Train train = trainService.patchTrainExercise(id, scheduleIndex, exerciseIndex, req);
        Train train = trainScheduleExerciseService.patchTrainExercise(id, scheduleIndex, exerciseIndex, req);
            return ResponseEntity.ok(train);
        }

    @PutMapping("/my-trains/{id}")
    public ResponseEntity<Train> updateTrainById(@PathVariable String id, @RequestBody TrainRequest updateTrainReq) {
        return ResponseEntity.ok(trainService.updateTrainById(id, updateTrainReq));
    }

    @DeleteMapping("/my-trains/{id}")
    public ResponseEntity<Void> deleteTrainById(@PathVariable String id) {
        trainService.deleteTrainById(id);
        return ResponseEntity.noContent().build();  
    }

}
