package com.trainday.train.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.trainday.train.api.DTO.request.ExerciseRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.repository.TrainRepository;


@Service
public class TrainScheduleExerciseService {

     private final TrainRepository trainRepository;

    public TrainScheduleExerciseService(TrainRepository trainRepository) {
        this.trainRepository = trainRepository;
    }


     public Train patchTrainScheduleById(String id, int index, TrainScheduleRequest req) {
        Train train = trainRepository.findById(id).orElseThrow(() -> new RuntimeException("Train not found"));
        
        List<TrainSchedule> schedules = train.getSchedules();

        if(index < 0 || index >= schedules.size()) {
            throw new RuntimeException("Schedule index out of bounds");
        }

        TrainSchedule schedule = schedules.get(index);

        if(req.weekday() != null) {
            schedule.setWeekday(req.weekday());
        }

        if(req.musclegroup() != null) {
            schedule.setMusclegroup(req.musclegroup());
        }

        if(req.emphasis() != null) {
            schedule.setEmphasis(req.emphasis());
        }



        schedules.set(index, schedule);
        train.setSchedules(schedules);
        
        return trainRepository.save(train);
    }
    
   public Train patchTrainExercise(
        String id,
        int scheduleIndex,
        int exerciseIndex,
        ExerciseRequest req) {

        Train train = trainRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("Train not found"));

        List<TrainSchedule> schedules = train.getSchedules();

        if (scheduleIndex < 0 || scheduleIndex >= schedules.size()) {
            throw new RuntimeException("Schedule index out of bounds");
        }

        List<Exercise> exercises = schedules.get(scheduleIndex).getExercise();

        if (exerciseIndex < 0 || exerciseIndex >= exercises.size()) {
            throw new RuntimeException("Exercise index out of bounds");
        }

        Exercise exercise = exercises.get(exerciseIndex);

        if (req.nameExercise() != null)
            exercise.setNameExercise(req.nameExercise());

        if (req.series() != null)
            exercise.setSeries(req.series());

        if (req.repetitions() != null)
            exercise.setRepetitions(req.repetitions());

        if (req.breakTime() != null)
            exercise.setBreakTime(req.breakTime());

        if (req.observation() != null)
            exercise.setObservation(req.observation());

        exercises.set(exerciseIndex, exercise);

        schedules.get(scheduleIndex).setExercise(exercises);

        return trainRepository.save(train);
    }


}
