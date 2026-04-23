package com.trainday.train.api.DTO.request;

public record ExerciseRequest(
    String nameExercise,
    Integer series,
    String repetitions,
    String breakTime,
    String observation
) {}
