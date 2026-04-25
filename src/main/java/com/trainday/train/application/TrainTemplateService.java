package com.trainday.train.application;

import java.util.List;

import org.springframework.stereotype.Service;
import com.trainday.train.domain.models.Train;

import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.domain.repository.TrainTemplateRepository;





@Service
public class TrainTemplateService {
    
    
    private final TrainTemplateRepository trainTemplateRepository;

    public TrainTemplateService(TrainTemplateRepository trainTemplateRepository) {
        this.trainTemplateRepository = trainTemplateRepository;
    }

    public TrainTemplate applyTemplateTrain(String templateId, String athleteId){

       TrainTemplate template = trainTemplateRepository.findById(templateId)
        .orElseThrow(() -> new RuntimeException("Template not found"));

        Train train = new Train();
        train.setAthleteId(athleteId);
        train.setNameTrain(template.getNameTrain());
        train.setCategory(template.getCategory());
        train.setDescription(template.getDescription());
        train.setSchedules(template.getSchedules());
        return trainTemplateRepository.save(template);
       
    }


    public List<TrainTemplate> getTrainTemplate() {
        return trainTemplateRepository.findAll();
    }

}




