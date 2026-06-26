package com.trainday.train.api.DTO.request;

import java.time.LocalDateTime;
import java.util.List;

import com.trainday.train.domain.models.enums.Role;

public record TrainRequest(
                String athleteId,
                String athletecpf,
                Role role,
                String cref,
                String nameTrain,
                String category,
                String description,
                LocalDateTime createdAt,
                List<TrainScheduleRequest> schedules

) {

}
