package com.trainday.train.application;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
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

            schedule.setExercise(exercises);
            return schedule;
        }).toList();

        train.setSchedules(schedules);
     

        Train saved = trainRepository.save(train);


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

                schedule.setExercise(exercises);
                return schedule;
            }).toList();

            train.setSchedules(schedules);
        }
        return trainRepository.save(train);
    }

    
    // public Train patchTrainScheduleById(String id, int index, TrainScheduleRequest req) {
    //     Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        
    //     List<TrainSchedule> schedules = train.getSchedules();

    //     if(index < 0 || index >= schedules.size()) {
    //         throw new RuntimeException("Schedule index out of bounds");
    //     }

    //     TrainSchedule schedule = schedules.get(index);

    //     if(req.weekday() != null) {
    //         schedule.setWeekday(req.weekday());
    //     }

    //     if(req.musclegroup() != null) {
    //         schedule.setMusclegroup(req.musclegroup());
    //     }

    //     if(req.emphasis() != null) {
    //         schedule.setEmphasis(req.emphasis());
    //     }

    //     if(req.exercises() != null) {
    //         List<Exercise> exercises = req.exercises().stream().map(exReq -> {
    //             Exercise exercise = new Exercise();
    //             exercise.setNameExercise(exReq.nameExercise());
    //             exercise.setSeries(exReq.series());
    //             exercise.setRepetitions(exReq.repetitions());
    //             exercise.setBreakTime(exReq.breakTime());
    //             exercise.setObservation(exReq.observation());
    //             return exercise;
    //         }).toList();

    //         schedule.setExercise(exercises);
    //     }

    //     schedules.set(index, schedule);
    //     train.setSchedules(schedules);
        
    //     return trainRepository.save(train);
    // }
    
    // public Train patchTrainExercise(
    //     String id,
    //     int scheduleIndex,
    //     int exerciseIndex,
    //     ExerciseRequest req) {

    //     Train train = trainRepository.findById(id)
    //         .orElseThrow(() -> new RuntimeException("Train not found"));

    //     List<TrainSchedule> schedules = train.getSchedules();

    //     if (scheduleIndex < 0 || scheduleIndex >= schedules.size()) {
    //         throw new RuntimeException("Schedule index out of bounds");
    //     }

    //     List<Exercise> exercises = schedules.get(scheduleIndex).getExercise();

    //     if (exerciseIndex < 0 || exerciseIndex >= exercises.size()) {
    //         throw new RuntimeException("Exercise index out of bounds");
    //     }

    //     Exercise exercise = exercises.get(exerciseIndex);

    //     if (req.nameExercise() != null)
    //         exercise.setNameExercise(req.nameExercise());

    //     if (req.series() != null)
    //         exercise.setSeries(req.series());

    //     if (req.repetitions() != null)
    //         exercise.setRepetitions(req.repetitions());

    //     if (req.breakTime() != null)
    //         exercise.setBreakTime(req.breakTime());

    //     if (req.observation() != null)
    //         exercise.setObservation(req.observation());

    //     exercises.set(exerciseIndex, exercise);

    //     schedules.get(scheduleIndex).setExercise(exercises);

    //     return trainRepository.save(train);
    // }
        
    public Train updateTrainById(String id, TrainRequest updateTrainReq) {
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));

        Optional.ofNullable(updateTrainReq.nameTrain()).ifPresent(train::setNameTrain);
        Optional.ofNullable(updateTrainReq.category()).ifPresent(train::setCategory);
        Optional.ofNullable(updateTrainReq.description()).ifPresent(train::setDescription);
        if (updateTrainReq.schedules() != null) {
            List<TrainSchedule> schedules = updateTrainReq.schedules().stream().map(scheduleReq -> {
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

                schedule.setExercise(exercises);
                return schedule;
            }).toList();

            train.setSchedules(schedules);
        }
        return trainRepository.save(train);
    }

    public Train deleteTrainById(String id){
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        trainRepository.delete(train);
        return train;
    }

}
