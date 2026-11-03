package com.trainlog.entity;


import com.trainlog.enums.SerieSessionStatusEnum;
import jakarta.persistence.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.Duration;
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
    @Column(precision = 7, scale = 2)
    private BigDecimal weight;
    @JdbcTypeCode(SqlTypes.INTERVAL_SECOND)
    private Duration time;
    @Enumerated(EnumType.STRING)
    private SerieSessionStatusEnum status;
    private Integer repetitions;
    @Column(precision = 7, scale = 2)
    private BigDecimal distance;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;


}
