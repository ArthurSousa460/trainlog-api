package com.trainlog.entity;

import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_series")
public class Serie {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private Integer position;
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration duration;
    private Integer repetitions;
    @Column(precision = 7, scale = 2)
    private BigDecimal distance;

    @ManyToOne
    @JoinColumn(name = "type_id", nullable = false)
    private ExerciseType exerciseType;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "serie", cascade = CascadeType.ALL)
    private List<SerieSession> serieSessions = new ArrayList<>();

}
