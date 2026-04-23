package com.trainday.train.api.DTO.response;

import java.time.LocalDateTime;
import java.util.List;

public record TrainResponse(
    String id,
    String athleteId,
    String nameTrain,
    String category,
    String description,
    LocalDateTime createdAt,
    List<TrainScheduleResponse> schedules

) {

}
