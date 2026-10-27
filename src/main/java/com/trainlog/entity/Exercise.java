package com.trainlog.entity;


import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "tb_exercises")
@Getter
@Setter
public class Exercise extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private String media_url;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ExerciseType exerciseType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToMany
    @JoinTable(
            name="tb_routine_exercises",
            joinColumns = @JoinColumn(name = "exercise_id"),
            inverseJoinColumns = @JoinColumn(name="routine_id")
    )
    private Set<Routine> routines = new HashSet<>();


    public Exercise() {

    }


    public Exercise(String name, String media_url, ExerciseType exerciseType, User user) {
        this.name = name;
        this.media_url = media_url;
        this.exerciseType = exerciseType;
        this.user = user;
    }
}
