package com.trainday.train.domain.repository;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainday.train.domain.models.TrainTemplate;

public interface TrainTemplateRepository extends MongoRepository<TrainTemplate, String> {}
