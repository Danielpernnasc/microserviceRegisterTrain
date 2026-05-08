package com.trainday.train.domain.models;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

public class Exercise {

    private String nameExercise;

    private Integer series;

    private  String repetitions;

    private String breakTime;

    private String observation;

   
}
