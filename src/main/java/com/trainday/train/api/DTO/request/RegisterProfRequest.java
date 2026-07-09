package com.trainday.train.api.DTO.request;

import com.trainday.train.domain.models.enums.Role;

public record RegisterProfRequest(
        String cref,
        String email,
        String password,
        Role Role) {

}
