package com.team.management.domain;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;

@Entity
@Getter
@Setter
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private String name;
    private String acronym;
    private BigDecimal budget;
    @JsonManagedReference
    @OneToMany(mappedBy = "team")
    private List<Player> playerList;
}
