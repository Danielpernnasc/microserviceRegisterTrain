package com.trainday.train.infra.client;

import com.trainday.train.infra.DTO.response.AthleteClientResponse;
import com.trainday.train.infra.config.FeignConfig;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "athlete-service", url = "http://localhost:8080", configuration = FeignConfig.class)
public interface AthleteClient {
    @GetMapping("/athlete/internal/cpf/{cpf}")
    AthleteClientResponse findByCpf(@PathVariable String cpf);
}
