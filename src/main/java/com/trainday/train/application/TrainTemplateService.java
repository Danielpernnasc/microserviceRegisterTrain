package com.trainday.train.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;
import com.trainday.train.domain.models.Train;

import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.domain.repository.TrainRepository;
import com.trainday.train.domain.repository.TrainTemplateRepository;





@Service
public class TrainTemplateService {
    
    
    private final TrainTemplateRepository trainTemplateRepository;
    private final TrainRepository trainRepository;

    public TrainTemplateService(TrainTemplateRepository trainTemplateRepository, TrainRepository trainRepository) {
        this.trainTemplateRepository = trainTemplateRepository;
        this.trainRepository = trainRepository;
    }

    public Train applyTemplateTrain(String templateId, String athleteId){

       TrainTemplate template = trainTemplateRepository.findById(templateId)
        .orElseThrow(() -> new RuntimeException("Template not found"));

        Train train = new Train();
        train.setNameTrain(template.getNameTrain());
        train.setAthleteId(athleteId);
        train.setCategory(template.getCategory());
        train.setDescription(template.getDescription());
        train.setCreatedAt(LocalDateTime.now());
        train.setSchedules(template.getSchedules());

       return trainRepository.save(train);
       
    }


    public List<TrainTemplate> getTrainTemplate() {
        return trainTemplateRepository.findAll();
    }

}




