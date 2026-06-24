package com.trainday.train.api.DTO.response;

public record LoginResponse(
        String id,
        String email,
        String cref,
        String name) {

}
