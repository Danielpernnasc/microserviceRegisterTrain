package com.trainday.train.api.DTO.request;

import java.util.List;

public record TrainScheduleRequest(
    String weekday,
    String musclegroup,
    String emphasis,
    List<ExerciseRequest> exercises
) {}
