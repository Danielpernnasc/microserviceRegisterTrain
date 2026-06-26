package com.trainday.train.infra.DTO.response;

import com.trainday.train.domain.models.enums.Role;

public record AthleteClientResponse(
        String id,
        String cpf,
        String name,
        String socialName,
        String email,
        String born,
        String gender,
        String identity,
        String height,
        String weight,
        Role role) {
}
