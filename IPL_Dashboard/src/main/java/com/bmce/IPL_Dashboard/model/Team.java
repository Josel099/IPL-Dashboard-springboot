package com.bmce.IPL_Dashboard.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Transient;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Team {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String teamName;

    private Long totalMatches;

    private Long totalWins;

    @Transient          // This field is not persisted in the database    
    private List<Match> matchs;

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
