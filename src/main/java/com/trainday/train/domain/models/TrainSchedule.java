package com.trainday.train.domain.models;

import java.util.ArrayList;
import java.util.List;

public class TrainSchedule {

        private String weekday;

        private String musclegroup;

        private String emphasis;

        private List<Exercise> exercises = new ArrayList<>();

        public TrainSchedule() {
        }

        public TrainSchedule(String weekday, String musclegroup, String emphasis, List<Exercise> exercises) {
                this.weekday = weekday;
                this.musclegroup = musclegroup;
                this.emphasis = emphasis;
                this.exercises = exercises;
        }

        public void addExercise(Exercise exercises) {
            this.exercises.add(exercises);

        }

        public String getWeekday() {
                return weekday;
        }

        public void setWeekday(String weekday) {
                this.weekday = weekday;
        }

        public String getMusclegroup() {
                return musclegroup;
        }

        public void setMusclegroup(String musclegroup) {
                this.musclegroup = musclegroup;
        }

        public String getEmphasis() {
                return emphasis;
        }
        public void setEmphasis(String emphasis) {
                this.emphasis = emphasis;       
        }

        public List<Exercise> getExercise() {
                return exercises;
        }


        public void setExercises(List<Exercise> exercises) {
                this.exercises = exercises;
        }
}
