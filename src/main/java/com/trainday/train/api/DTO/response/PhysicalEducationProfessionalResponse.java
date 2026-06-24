package com.trainday.train.api.DTO.response;

import com.trainday.train.domain.models.enums.Role;

public record PhysicalEducationProfessionalResponse(
                String name,
                String email,
                String cref,
                String phone,
                String cpf,
                String address,
                java.time.LocalDate bornDate,
                Role role) {

}
