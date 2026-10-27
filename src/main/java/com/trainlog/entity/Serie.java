package com.trainlog.entity;

import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_serie")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer position;
    private LocalTime duration;
    private Integer repetitions;
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ExerciseType exerciseType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<SerieSession> serieSessions = new ArrayList<>();

}
