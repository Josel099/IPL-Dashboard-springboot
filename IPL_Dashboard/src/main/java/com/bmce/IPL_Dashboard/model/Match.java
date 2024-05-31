package com.bmce.IPL_Dashboard.model;

import java.time.LocalDate;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "match")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String city;

    private LocalDate date;

    private String playerOfMatch;

    private String venue;

    private String team1;

    private String team2;

    private String tossWinner;

    private String tossDecision;

    private String matchWinner;

    private String result;

    private String resultMargin;

    private String umpire1;

    private String umpire2;

}
