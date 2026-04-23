package com.trainday.train.api.DTO.response;

import java.util.List;

public record TrainScheduleResponse(
    String weekday,
    String musclegroup,
    String emphasis,
    List<ExerciseResponse> exercises
){
  
}
