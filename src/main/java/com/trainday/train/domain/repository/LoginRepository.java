package com.trainday.train.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainday.train.domain.models.LoginPhyEdProf;

public interface LoginRepository extends MongoRepository<LoginPhyEdProf, String> {
    Optional<LoginPhyEdProf> findByCref(String cref);

    Optional<LoginPhyEdProf> findByEmail(String email);

}
