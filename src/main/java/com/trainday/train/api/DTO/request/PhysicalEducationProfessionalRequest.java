package com.trainday.train.api.DTO.request;

import java.time.LocalDate;

import com.trainday.train.domain.models.enums.Role;

public record PhysicalEducationProfessionalRequest(
                String name,
                LocalDate bornDate,
                String cpf,
                String phone,
                String address,
                Role role) {

}
