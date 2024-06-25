package com.example.application.data;

import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class ContractEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="leader_id")
    private LeaderEntity leader;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="team_id")
    private TeamEntity team;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="projectPart_id")
    private ProjectPartEntity projectPart;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="controller_id")
    private ControllerEntity controller;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name ="experts_id")
    private ExpertsEntity experts;


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - \\
}