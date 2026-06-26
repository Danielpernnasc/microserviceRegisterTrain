package com.trainday.train.domain.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.trainday.train.domain.models.Train;

@Repository
public interface TrainRepository extends MongoRepository<Train, String> {
    List<Train> findByAthleteCpf(String athleteCpf);

}
