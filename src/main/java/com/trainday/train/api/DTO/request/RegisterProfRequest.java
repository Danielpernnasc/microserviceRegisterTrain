package com.trainday.train.api.DTO.request;

public record RegisterProfRequest(
        String cref,
        String email,
        String password) {

}
