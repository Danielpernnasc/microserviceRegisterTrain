package com.trainday.train.api.DTO.request;

import java.util.List;

public record TrainTemplateReq(
    String nameTrain,
    String category,
    String description,
    List<TrainScheduleRequest> schedules
) {

}
