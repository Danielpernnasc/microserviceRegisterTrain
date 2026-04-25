package com.trainday.train.api.DTO.response;

import java.util.List;

import com.trainday.train.api.DTO.request.TrainScheduleRequest;

public record TrainTemplateResponse(
    String id,
    String nameTrain,
    String category,
    String description,
    List<TrainScheduleRequest> schedules
) {


}
