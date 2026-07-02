package com.trainday.train.domain.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;

import com.trainday.train.domain.models.PhysicalEducationProfessional;

public interface RepositoryPhyEdProf extends MongoRepository<PhysicalEducationProfessional, String> {
    Optional<PhysicalEducationProfessional> findByCref(String cref);

    Optional<PhysicalEducationProfessional> findByEmail(String email);

}
