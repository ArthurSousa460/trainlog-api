package com.trainlog.controllers;
import com.trainlog.dto.requests.ExerciseRequestDTO;
import com.trainlog.dto.responses.ResponseExerciseDTO;
import com.trainlog.service.ExerciseService;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/exercises")
@AllArgsConstructor
public class ExerciseController {

    private final ExerciseService exerciseService;

    @PostMapping
    public ResponseEntity<ResponseExerciseDTO> create(
            @RequestBody ExerciseRequestDTO request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ResponseExerciseDTO(exerciseService.save(request)));
    }

    @GetMapping
    public ResponseEntity<Page<ResponseExerciseDTO>> list(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int pageSize
    ){
        Pageable pageable = PageRequest.of(page, pageSize);
        return ResponseEntity.ok(
                exerciseService.list(pageable).map(
                        ResponseExerciseDTO::new
                )
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseExerciseDTO> findById(
            @PathVariable UUID id
    ) {
        return ResponseEntity.ok(
                new ResponseExerciseDTO(exerciseService.findById(id))
        );
    }

    @PutMapping("/{id}")
    public ResponseEntity<ResponseExerciseDTO> update(
            @PathVariable UUID id,
            @RequestBody ExerciseRequestDTO request
    ) {
        return ResponseEntity.ok(
                new ResponseExerciseDTO(exerciseService.update(id, request))
        );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @PathVariable UUID id
    ) {
        exerciseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
