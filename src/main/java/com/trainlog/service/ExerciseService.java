package com.trainlog.service;

import com.trainlog.entity.Exercise;
import com.trainlog.repository.ExerciseRepository;
import com.trainlog.repository.ExerciseTypeRepository;
import com.trainlog.exceptions.ResourceNotFoundException;
import lombok.Data;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@Data
public class ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final ExerciseTypeRepository exerciseTypeRepository;

    public ExerciseService(ExerciseRepository exerciseRepository,ExerciseTypeRepository exerciseTypeRepository){
        this.exerciseRepository = exerciseRepository;
        this.exerciseTypeRepository = exerciseTypeRepository;
    }

    public Page<Exercise> list(Pageable pageable){
        return exerciseRepository.findAll(pageable);
    }

    public Exercise findById(UUID id){
        return  exerciseRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Excercicio não encontrado")
                );
    }

//    public Exercise save(RequestExerciseDTO exercise){
//       boolean exerciseExist = exerciseRepository.existsByName(exercise.name());
//       Optional<ExerciseType> exerciseTypeExist = exerciseTypeRepository.findById(exercise.type());
//       if(exerciseTypeExist.isEmpty()) throw  new ResourceNotFoundException("Tipo de exercício não existente!");
//       if(exerciseExist) throw new BusinessException(String.format("O exercício %s já existe", exercise.name()));
//       Exercise newExercise = new Exercise(
//               exercise.name(),
//               exercise.media_url(),
//               exercise.type(),
//
//
//       );
//    }
}
