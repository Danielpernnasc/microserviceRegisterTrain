package com.trainday.train.application.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.mongodb.DuplicateKeyException;
import com.trainday.train.api.DTO.request.PhysicalEducationProfessionalRequest;
import com.trainday.train.api.DTO.response.PhysicalEducationProfessionalResponse;
import com.trainday.train.domain.models.LoginPhyEdProf;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.repository.LoginRepository;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;

@Service
public class PhysicalEducationProfessionalService {

    private final RepositoryPhyEdProf repositorPhyEdProf;
    private final LoginRepository loginRepository;

    public PhysicalEducationProfessionalService(RepositoryPhyEdProf repositorPhyEdProf,
            LoginRepository loginRepository) {
        this.repositorPhyEdProf = repositorPhyEdProf;
        this.loginRepository = loginRepository;
    }

    public boolean isCrefRegistered(String cref) {
        return !repositorPhyEdProf.findByCref(cref).isEmpty();
    }

    public PhysicalEducationProfessional create(String email, PhysicalEducationProfessionalRequest req) {

        LoginPhyEdProf user = loginRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (repositorPhyEdProf.findByCref(email).isPresent()) {
            throw new RuntimeException("CREF already registered");
        }

        PhysicalEducationProfessional professional = new PhysicalEducationProfessional();
        professional.setName(req.name());
        professional.setEmail(user.getEmail());
        professional.setCref(user.getCref());
        professional.setBornDate(req.bornDate());
        professional.setCpf(req.cpf());
        professional.setPhone(req.phone());
        professional.setAddress(req.address());
        professional.setRole(req.role());

        try {
            return repositorPhyEdProf.save(professional);
        } catch (Exception e) {
            throw new RuntimeException("Error creating Physical Education Professional: " + e.getMessage());
        }

    }

    public PhysicalEducationProfessionalResponse getByCref(String cref) {
        PhysicalEducationProfessional pep = repositorPhyEdProf.findByCref(cref)
                .orElseThrow(() -> new RuntimeException("Physical Education Professional not found"));

        return new PhysicalEducationProfessionalResponse(
                pep.getName(),
                pep.getEmail(),
                pep.getCref(),
                pep.getPhone(),
                pep.getCpf(),
                pep.getAddress(),
                pep.getBornDate(),
                pep.getRole());

    }

    public PhysicalEducationProfessional UpdatePEP(String cref, PhysicalEducationProfessionalRequest req) {

        repositorPhyEdProf.findAll()
                .forEach(p -> {
                    if (!p.getCref().equals(cref) && p.getEmail().equals(req.name())) {
                        throw new RuntimeException("Email already registered");
                    }
                });

        PhysicalEducationProfessional pep = repositorPhyEdProf.findByCref(cref)
                .orElseThrow(() -> new RuntimeException("Physical Education Professional not found"));

        Optional.ofNullable(req.name()).ifPresent(pep::setName);
        Optional.ofNullable(req.bornDate()).ifPresent(pep::setBornDate);
        Optional.ofNullable(req.cpf()).ifPresent(pep::setCpf);
        Optional.ofNullable(req.phone()).ifPresent(pep::setPhone);
        Optional.ofNullable(req.address()).ifPresent(pep::setAddress);
        Optional.ofNullable(req.role()).ifPresent(pep::setRole);

        try {
            return repositorPhyEdProf.save(pep);
        } catch (DuplicateKeyException e) {
            throw new RuntimeException("CREF or Email already registered");
        }
    }

    public PhysicalEducationProfessional patchPEP(String cref, PhysicalEducationProfessionalRequest req) {
        PhysicalEducationProfessional pep = repositorPhyEdProf.findByCref(cref)
                .orElseThrow(() -> new RuntimeException("Physical Education Professional not found"));

        if (req.name() != null) {
            pep.setName(req.name());
        }

        if (req.bornDate() != null) {
            pep.setBornDate(req.bornDate());
        }

        if (req.cpf() != null) {
            pep.setCpf(req.cpf());
        }

        if (req.phone() != null) {
            pep.setPhone(req.phone());
        }

        if (req.address() != null) {
            pep.setAddress(req.address());
        }

        if (req.role() != null) {
            pep.setRole(req.role());
        }

        return repositorPhyEdProf.save(pep);
    }

    public void deletePEP(String cref) {
        PhysicalEducationProfessional pep = repositorPhyEdProf.findByCref(cref)
                .orElseThrow(() -> new RuntimeException("Physical Education Professional not found"));

        String userId = pep.getId();
        repositorPhyEdProf.deleteById(cref);
        loginRepository.deleteById(userId);
    }
}
