package com.trainlog.service;

import com.trainlog.entity.Exercise;
import com.trainlog.entity.ExerciseType;
import com.trainlog.entity.User;
import com.trainlog.dto.requests.ExerciseRequestDTO;
import com.trainlog.exceptions.BusinessException;
import com.trainlog.repository.ExerciseRepository;
import com.trainlog.repository.ExerciseTypeRepository;
import com.trainlog.repository.UserRepository;
import com.trainlog.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

import static com.trainlog.utils.FindUserId.authenticatedUserId;

@Service
@Data
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;
    private final UserRepository userRepository;

    public ExerciseService(
            ExerciseRepository exerciseRepository,
            ExerciseTypeRepository exerciseTypeRepository,
            UserRepository userRepository
    ){
        this.exerciseRepository = exerciseRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
        this.userRepository = userRepository;
    }
    @Transactional(readOnly = true)
    public Page<Exercise> list(Pageable pageable){
        return exerciseRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Exercise findById(UUID id){
        return  exerciseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Excercicio não encontrado")
                );
    }

    @Transactional
    public Exercise save(ExerciseRequestDTO request) {
        UUID userId = authenticatedUserId();

        if (exerciseRepository.existsByNameAndUser_Id(request.name(), userId)) {
            throw new BusinessException(
                    String.format("O exercício %s já existe", request.name())
            );
        }

        ExerciseType exerciseType = exerciseTypeRepository.findById(request.type())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Tipo de exercício não existente!")
                );

        User user = userRepository.findById(userId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Usuário não encontrado")
                );

        Exercise newExercise = new Exercise(
                request.name(),
                request.media_url(),
                exerciseType,
                user
        );

        return exerciseRepository.save(newExercise);
    }

    @Transactional
    public Exercise update(UUID id, ExerciseRequestDTO request){
        UUID userId = authenticatedUserId();
        Exercise exerciseToUpdate = exerciseRepository.findByIdAndUser_id(id, userId)
                .orElseThrow(() -> new ResourceNotFoundException("Exercício não encontrado"));

        if (request.name() != null && !request.name().isBlank()) {
            if (!request.name().equals(exerciseToUpdate.getName())
                    && exerciseRepository.existsByNameAndUser_IdAndIdNot(
                    request.name(), userId, id
            )) {
                throw new BusinessException(
                        String.format("O exercício %s já existe", request.name())
                );
            }
            exerciseToUpdate.setName(request.name());
        }

        if (request.media_url() != null && !request.media_url().isBlank()) {
            exerciseToUpdate.setMedia_url(request.media_url());
        }

        if (request.type() != null) {
            ExerciseType exerciseType = exerciseTypeRepository.findById(request.type())
                    .orElseThrow(() ->
                            new ResourceNotFoundException("Tipo de exercício não existente!")
                    );
            exerciseToUpdate.setExerciseType(exerciseType);
        }

        return exerciseRepository.save(exerciseToUpdate);

    }

    @Transactional
    public void delete(UUID id){
        UUID user_id = authenticatedUserId();
        Exercise existExercise = exerciseRepository.findByIdAndUser_id(id, user_id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Exercício não encontrado!")
                        );
        exerciseRepository.delete(existExercise);
    }
}
