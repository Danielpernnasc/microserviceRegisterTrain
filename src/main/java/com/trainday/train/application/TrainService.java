package com.trainday.train.application;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.stereotype.Service;

import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.repository.TrainRepository;


@Service
public class TrainService {

    private final TrainRepository trainRepository;

    public TrainService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }

    public Train createTrain(TrainRequest req, String athleteId) {
        Train train = new Train();
        train.setNameTrain(req.nameTrain());
        train.setAthleteId(athleteId);
        train.setCategory(req.category());
        train.setDescription(req.description());
        train.setCreatedAt(req.createdAt() != null ? req.createdAt() : LocalDateTime.now());
           List<TrainSchedule> schedules = req.schedules().stream().map(scheduleReq -> {
            TrainSchedule schedule = new TrainSchedule();
            schedule.setWeekday(scheduleReq.weekday());
            schedule.setMusclegroup(scheduleReq.musclegroup());
            schedule.setEmphasis(scheduleReq.emphasis());

            List<Exercise> exercises = scheduleReq.exercises().stream().map(exReq -> {
                Exercise exercise = new Exercise();
                exercise.setNameExercise(exReq.nameExercise());
                exercise.setSeries(exReq.series());
                exercise.setRepetitions(exReq.repetitions());
                exercise.setBreakTime(exReq.breakTime());
                exercise.setObservation(exReq.observation());
                return exercise;
            }).toList();

            schedule.setExercises(exercises);
            return schedule;
        }).toList();

        train.setSchedules(schedules);
         System.out.println("ANTES DO SAVE");

        Train saved = trainRepository.save(train);

        System.out.println("DEPOIS DO SAVE");

        return saved;
    }

 
    public List<Train> listTrains() {
        return trainRepository.findAll();
    }

    public Train getTrainById(String id) {
        return trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
    }

    public Train patchTrainById(String id, TrainRequest req) {
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        if (req.nameTrain() != null) {
            train.setNameTrain(req.nameTrain());
        }
        if (req.category() != null) {
            train.setCategory(req.category());
        }
        if (req.description() != null) {
            train.setDescription(req.description());
        }
        if (req.schedules() != null) {
            List<TrainSchedule> schedules = req.schedules().stream().map(scheduleReq -> {
                TrainSchedule schedule = new TrainSchedule();
                schedule.setWeekday(scheduleReq.weekday());
                schedule.setMusclegroup(scheduleReq.musclegroup());
                schedule.setEmphasis(scheduleReq.emphasis());

                List<Exercise> exercises = scheduleReq.exercises().stream().map(exReq -> {
                    Exercise exercise = new Exercise();
                    exercise.setNameExercise(exReq.nameExercise());
                    exercise.setSeries(exReq.series());
                    exercise.setRepetitions(exReq.repetitions());
                    exercise.setBreakTime(exReq.breakTime());
                    exercise.setObservation(exReq.observation());
                    return exercise;
                }).toList();

                schedule.setExercises(exercises);
                return schedule;
            }).toList();

            train.setSchedules(schedules);
        }
        return trainRepository.save(train);
    }

    public Train updateTrainById(String id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        train.setNameTrain("Treino atualizado");
        return trainRepository.save(train);
    }

    public Train deleteTrainById(String id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        trainRepository.delete(train);
        return train;
    }
    



}
