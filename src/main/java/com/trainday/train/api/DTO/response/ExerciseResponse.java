package com.trainday.train.api.DTO.response;

public record ExerciseResponse(
    String nameExercise,
    int series,
    int repetitions,
    int breakTime,
    String observation
) {

}
