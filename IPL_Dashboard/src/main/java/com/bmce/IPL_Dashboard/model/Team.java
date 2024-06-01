package com.bmce.IPL_Dashboard.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    private Long totalMatches;

    private Long totalWins;

    
    public Team(String teamName, Long totalMatches) {
        super();
        this.teamName = teamName;
        this.totalMatches = totalMatches;
    }


    @Override
    public String toString() {
        return "Team [teamName=" + teamName + ", totalMatches=" + totalMatches + ", totalWins="
            + totalWins + "]";
    }
    
    

}