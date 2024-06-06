package com.bmce.IPL_Dashboard.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bmce.IPL_Dashboard.model.Match;

@Repository
public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1 , String teamName2,Pageable pageable); 
}
