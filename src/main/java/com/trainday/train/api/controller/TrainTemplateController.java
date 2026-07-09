package com.trainday.train.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.train.application.service.TrainService;
import com.trainday.train.application.service.TrainTemplateService;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.infra.service.JwtService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.slf4j.LoggerFactory;

import java.util.List;

import org.slf4j.Logger;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/trainTemplate")
public class TrainTemplateController {

    private final TrainTemplateService trainTemplateService;
    private static final Logger log = LoggerFactory.getLogger(TrainService.class);
    private final JwtService jwtService;

    public TrainTemplateController(
            TrainTemplateService trainTemplateService,
            JwtService jwtService) {

        this.trainTemplateService = trainTemplateService;
        this.jwtService = jwtService;
    }

    @PostMapping("/templates/{id}/apply")
    public ResponseEntity<Train> applyTemplateTrain(
            @PathVariable String id,
            @RequestHeader("Authorization") String authHeader

    ) {
        log.info("Received request to create train: {}", id);
        String token = authHeader.substring(7);
        String athleteId = jwtService.extractSubject(token);
        Train applyTrain = trainTemplateService.applyTemplateTrain(id, athleteId);
        return ResponseEntity.status(HttpStatus.CREATED).body(applyTrain);
    }

    @GetMapping("/templates")
    public ResponseEntity<List<TrainTemplate>> getTrainTemplate() {
        return ResponseEntity.ok(trainTemplateService.getTrainTemplate());
    }

}
