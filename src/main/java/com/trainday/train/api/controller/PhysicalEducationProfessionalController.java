package com.trainday.train.api.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.trainday.train.api.DTO.request.PhysicalEducationProfessionalRequest;
import com.trainday.train.api.DTO.response.PhysicalEducationProfessionalResponse;
import com.trainday.train.application.PhysicalEducationProfessionalService;
import com.trainday.train.domain.models.PhysicalEducationProfessional;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;

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

@RestController
@RequestMapping("/PEP")
@SecurityRequirement(name = "bearerAuth")
public class PhysicalEducationProfessionalController {

    private final PhysicalEducationProfessionalService service;

    public PhysicalEducationProfessionalController(PhysicalEducationProfessionalService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<PhysicalEducationProfessional> save(
            @RequestBody PhysicalEducationProfessionalRequest req,
            Authentication authentication) {

        String cref = authentication.getName();
        System.out.println("AUTH NAME = " + authentication.getName());

        PhysicalEducationProfessional createdPEP = service.create(cref, req);

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(createdPEP);
    }

    @GetMapping("/CREF/{cref}")
    public ResponseEntity<PhysicalEducationProfessionalResponse> findByCREF(
            @PathVariable String cref) {
        PhysicalEducationProfessionalResponse pep = service.getByCref(cref);

        return ResponseEntity.ok(new PhysicalEducationProfessionalResponse(
                pep.name(),
                pep.email(),
                pep.cref(),
                pep.phone(),
                pep.cpf(),
                pep.address(),
                pep.bornDate(),
                pep.role()));
    }

    @PutMapping("/CREF/{cref}")
    public PhysicalEducationProfessional updateByCREF(
            @PathVariable String cref,
            @RequestBody PhysicalEducationProfessionalRequest req) {

        return service.UpdatePEP(cref, req);
    }

    @PatchMapping("/CREF/{cref}")
    public PhysicalEducationProfessional patchByCREF(
            @PathVariable String cref,
            @RequestBody PhysicalEducationProfessionalRequest req) {

        return service.UpdatePEP(cref, req);
    }

    @DeleteMapping("/CREF/{cref}")
    public void deleteByCREF(@PathVariable String cref) {
        service.deletePEP(cref);
    }

}
