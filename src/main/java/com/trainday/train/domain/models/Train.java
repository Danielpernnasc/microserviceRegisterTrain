package com.trainday.train.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "train")
public class Train {
    @Id
    private String id;
    private String athleteId;
    private String  nameTrain;
    private String category;

    private String description;

    private LocalDateTime createdAt;

    private List<TrainSchedule> schedules;

    public Train() {
    }


    public Train(String id, String atheleteId, String nameTrain, String category, String description, LocalDateTime createdAt, List<TrainSchedule> schedules) {
        this.id = id;
        this.athleteId = atheleteId;
        this.nameTrain = nameTrain;
        this.category = category;
        this.description = description;
        this.createdAt = createdAt;
        this.schedules = schedules;
    }

    
    

    @org.springframework.data.annotation.Transient
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = java.time.LocalDateTime.now();
        }
    }

    public String getAthleteId() {
        return athleteId;
    }

    public void setAthleteId(String athleteId) {
        this.athleteId = athleteId;
    }

    public String getId() {
        return id;
    }


    public void setId(String id) {
        this.id = id;
    }

    public String getNameTrain() {
        return nameTrain;
    }

    public void setNameTrain(String nameTrain) {
        this.nameTrain = nameTrain;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public List<TrainSchedule> getSchedules() {
        return schedules;
    }

    public void setSchedules(List<TrainSchedule> schedules) {
        this.schedules = schedules;
    }

}
