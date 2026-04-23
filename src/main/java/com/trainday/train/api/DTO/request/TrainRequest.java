package com.trainday.train.api.DTO.request;


import java.time.LocalDateTime;
import java.util.List;

public record TrainRequest(
    String nameTrain,
    String category,
    String description,
    LocalDateTime createdAt,
   List<TrainScheduleRequest> schedules

) {}
