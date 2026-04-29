package com.trainday.train.infra.seed;

import java.io.InputStream;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.trainday.train.domain.models.TrainTemplate;
import com.trainday.train.domain.repository.TrainTemplateRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class TemplateSeed implements CommandLineRunner {

    private final TrainTemplateRepository repository;
    private final ObjectMapper objectMapper;

    public TemplateSeed(TrainTemplateRepository repository, ObjectMapper object) {
        this.repository = repository;
        this.objectMapper = object;
    }

    @Override
    public void run(String... args) throws Exception {


        if (repository.count() == 0) {
            saveFile("trains/treinocompleto_NaturalPhysique.json");
            saveFile("trains/treinocompleto_MensPhysique.json");
            saveFile("trains/treinocompleto_ClassicPhysique.json");
            saveFile("trains/treinocompleto_Open.json");
        }


    }

  

    private void saveFile(String path) throws Exception {

    InputStream is = getClass().getClassLoader().getResourceAsStream(path);

    if (is == null) {
        throw new RuntimeException("Arquivo não encontrado: " + path);
    }

      TrainTemplate template = objectMapper.readValue(is, TrainTemplate.class);
      repository.save(template);
    }

}
