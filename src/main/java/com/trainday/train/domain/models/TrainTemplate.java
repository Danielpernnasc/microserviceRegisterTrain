package com.trainday.train.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor

@Document(collection = "train_templates")
public class TrainTemplate {
    @Id
    private String id;
    private String athleteId;
    private String nameTrain;
    private String category;
    private String description;
    @CreatedDate
    private LocalDateTime createdAt;
    private List<TrainSchedule> schedules;

}
