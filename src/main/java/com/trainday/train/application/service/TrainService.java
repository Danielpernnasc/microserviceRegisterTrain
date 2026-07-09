package com.trainday.train.application.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.trainday.train.api.DTO.request.TrainRequest;
import com.trainday.train.api.DTO.request.TrainScheduleRequest;
import com.trainday.train.domain.models.Exercise;
import com.trainday.train.domain.models.PhysicalEducationProfessional;
import com.trainday.train.domain.models.Train;
import com.trainday.train.domain.models.TrainSchedule;
import com.trainday.train.domain.models.enums.Role;
import com.trainday.train.domain.repository.RepositoryPhyEdProf;
import com.trainday.train.domain.repository.TrainRepository;
import com.trainday.train.infra.DTO.response.AthleteClientResponse;
import com.trainday.train.infra.client.AthleteClient;

@Service
public class TrainService {

    private final TrainRepository trainRepository;
    private final AthleteClient athleteClient;
    private final RepositoryPhyEdProf repositoryepe;

    public TrainService(
            TrainRepository trainRepository,
            AthleteClient athleteClient,
            RepositoryPhyEdProf repositoryepe) {
        this.trainRepository = trainRepository;
        this.athleteClient = athleteClient;
        this.repositoryepe = repositoryepe;
    }

    public Train createTrain(TrainRequest req,
            String email) {

        // Verifica se o atleta existe no Athlete Service
        AthleteClientResponse athlete = athleteClient.findByCpf(req.athletecpf());

        // Verifica se o profissional existe no banco
        PhysicalEducationProfessional professional = repositoryepe.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Professional not found"));
        if (repositoryepe.findByCref(email).isPresent()) {
            throw new RuntimeException("CREF already registered");
        }

        Train train = new Train();

        train.setAthleteId(athlete.id());
        train.setAthleteCpf(athlete.cpf());
        train.setAthleteName(athlete.name());
        train.setAthleteemail(athlete.email());
        train.setRoleAthlete(athlete.role());

        train.setProfessionalId(professional.getId());
        train.setCref(professional.getCref());
        train.setNameProfessional(professional.getName());
        train.setNameTrain(req.nameTrain());
        train.setCategory(req.category());
        train.setDescription(req.description());
        train.setRoleprofessional(Role.PERSONAL_TRAINER);
        train.setCreatedAt(LocalDateTime.now());

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

        Train saved = trainRepository.save(train);

        return saved;
    }

    public List<Train> getTrainByCpf(String cpf) {

        return trainRepository.findByAthleteCpf(cpf);
    }

    private List<TrainSchedule> mapSchedules(List<TrainScheduleRequest> scheduleReqs) {
        return scheduleReqs.stream().map(scheduleReq -> {
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
    }

    public Train patchTrainByCpf(String cpf, TrainRequest req) {
        List<Train> trains = trainRepository.findByAthleteCpf(cpf);
        Train train = trains.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Train not found"));
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
            train.setSchedules(mapSchedules(req.schedules()));
        }
        return trainRepository.save(train);
    }

    public Train updateTrainByCpf(String cpf, TrainRequest updateTrainReq) {
        List<Train> trains = trainRepository.findByAthleteCpf(cpf);
        Train train = trains.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Train not found"));

        PhysicalEducationProfessional professional = repositoryepe.findByCref(updateTrainReq.cref())
                .orElseThrow(() -> new RuntimeException("Professional not found"));

        train.setProfessionalId(professional.getId());
        train.setCref(professional.getCref());
        train.setNameProfessional(professional.getName());

        if (updateTrainReq != null) {
            Optional.ofNullable(updateTrainReq.nameTrain()).ifPresent(train::setNameTrain);
            Optional.ofNullable(updateTrainReq.category()).ifPresent(train::setCategory);
            Optional.ofNullable(updateTrainReq.description()).ifPresent(train::setDescription);
            if (updateTrainReq.schedules() != null) {
                train.setSchedules(mapSchedules(updateTrainReq.schedules()));
            }
        }
        return trainRepository.save(train);
    }

    public Train deleteTrainByCpf(String cpf) {
        List<Train> trains = trainRepository.findByAthleteCpf(cpf);
        Train train = trains.stream()
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Train not found"));
        trainRepository.delete(train);
        return train;
    }

}
