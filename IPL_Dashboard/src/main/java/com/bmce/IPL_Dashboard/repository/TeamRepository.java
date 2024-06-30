package com.bmce.IPL_Dashboard.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bmce.IPL_Dashboard.model.Team;

@Repository
public interface TeamRepository extends CrudRepository<Team, Long>{

    Team findByTeamName(String teamName);
}