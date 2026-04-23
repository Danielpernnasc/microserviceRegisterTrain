package com.trainday.train.domain.models;

public class Exercise {

    private String nameExercise;

    private Integer series;

    private  String repetitions;

    private String breakTime;

    private String observation;

    public Exercise() {
    }

    public Exercise(String nameExercise, Integer series, String repetitions, String breakTime, String observation) {
        this.nameExercise = nameExercise;
        this.series = series;
        this.repetitions = repetitions;
        this.breakTime = breakTime;
        this.observation = observation;
    }

    public String getNameExercise() {
        return nameExercise;
    }

    public void setNameExercise(String nameExercise) {
        this.nameExercise = nameExercise;
    }

    public Integer getSeries() {
        return series;
    }

    public void setSeries(Integer series) {
        this.series = series;
    }

    public String getRepetitions() {
        return repetitions;
    }

    public void setRepetitions(String repetitions) {
        this.repetitions = repetitions;
    }

    public String getBreakTime() {
        return breakTime;
    }

    public void setBreakTime(String breakTime) {
        this.breakTime = breakTime;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

}
