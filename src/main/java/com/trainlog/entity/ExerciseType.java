package com.trainlog.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_exercise_types")
@Data
public class ExerciseType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;



    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL)
    private List<Exercise> exercises = new ArrayList<>();

    @OneToMany(mappedBy = "exerciseType", cascade = CascadeType.ALL)
    private List<Serie> series = new ArrayList<>();

}
