package com.trainday.train.domain.models;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class TrainSchedule {

        private String weekday;

        private String musclegroup;

        private String emphasis;

        private List<Exercise> exercises = new ArrayList<>();

        public void addExercise(Exercise exercises) {
            this.exercises.add(exercises);

        }

       
}
