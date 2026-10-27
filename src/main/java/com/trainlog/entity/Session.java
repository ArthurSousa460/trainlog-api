package com.trainlog.entity;

import com.trainlog.enums.SessionStatusEnum;
import jakarta.persistence.*;
import lombok.Data;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "tb_sessions")
@Data
public class Session extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    @Enumerated(EnumType.STRING)
    private SessionStatusEnum status;
    private Instant finalizedAt;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @OneToMany(mappedBy = "session", cascade = CascadeType.ALL)
    private List<SerieSession> serieSessionList =  new ArrayList<>();

}
