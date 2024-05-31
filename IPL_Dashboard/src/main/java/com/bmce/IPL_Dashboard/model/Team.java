package com.bmce.IPL_Dashboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Team {

    @Id
    private Long id;

    private String teamName;

    private Long totalMatches;

    private Long totalWins;

}