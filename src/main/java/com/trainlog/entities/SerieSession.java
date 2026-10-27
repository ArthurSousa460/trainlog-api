package com.trainlog.entities;


import com.trainlog.enums.SerieSessionStatusEnum;
import jakarta.persistence.*;

import java.time.LocalTime;
import java.util.UUID;

@Entity
@Table(name = "tb_series_session")
public class SerieSession {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;


    @ManyToOne
    @JoinColumn(name="session_id", nullable = false)
    private Session session;

    @ManyToOne
    @JoinColumn(name="serie_id", nullable = false)
    private Serie serie;

    private String exercise_type;
    private double weight;
    private LocalTime time;
    @Enumerated(EnumType.STRING)
    private SerieSessionStatusEnum status;
    private Integer repetitions;
    private Double distance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
