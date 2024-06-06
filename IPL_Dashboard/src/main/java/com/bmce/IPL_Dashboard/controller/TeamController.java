package com.bmce.IPL_Dashboard.controller;



import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.bmce.IPL_Dashboard.model.Team;
import com.bmce.IPL_Dashboard.repository.MatchRepository;
import com.bmce.IPL_Dashboard.repository.TeamRepository;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
public class TeamController {

    private final TeamRepository teamRepository;

    private final MatchRepository matchRepository;


    @GetMapping("/team/{teamName}")
    public Team getTeam(@PathVariable String teamName) {
        Pageable pageable = PageRequest.of(0,4);
        Team team = this.teamRepository.findByTeamName(teamName);
        team.setMatchs(matchRepository.getByTeam1OrTeam2OrderByDateDesc(teamName, teamName,pageable));
        
        return team;
    }

}
