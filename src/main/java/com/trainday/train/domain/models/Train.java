package com.trainday.train.domain.models;

import java.time.LocalDateTime;
import java.util.List;

import com.trainday.train.domain.models.enums.Role;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "train")
public class Train {
    @Id
    private String id;
    private String athleteId;
    private String athleteCpf;
    private String athleteName;
    private String athleteemail;
    private Role roleAthlete;

    private String nameTrain;
    private String category;
    private Role roleprofessional;
    private String professionalId;
    private String nameProfessional;
    private String cref;
    private String description;

    @CreatedDate
    private LocalDateTime createdAt;

    private List<TrainSchedule> schedules;

    @Transient
    public void prePersist() {
        if (this.createdAt == null) {
            this.createdAt = java.time.LocalDateTime.now();
        }
    }

}
