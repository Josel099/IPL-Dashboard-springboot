package com.bmce.IPL_Dashboard.data;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import com.bmce.IPL_Dashboard.model.Team;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;

@Component
public class JobCompletionNotificationListener implements JobExecutionListener {

    private static final Logger log = LoggerFactory
        .getLogger(JobCompletionNotificationListener.class);

    private final EntityManager em;


    public JobCompletionNotificationListener(EntityManager em) {
        this.em = em;
    }


    Map<String, Team> teamData = new HashMap<>();


    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");

            
            // finding the total matched played by each team 
           //@todo : handle an exception -> if an team always in the 2 nd position it will not be find. 
            em.createQuery("SELECT m.team1, count(*) FROM Match m GROUP BY m.team1", Object[].class)
            .getResultList()
            .stream()
            .map(e -> new Team((String) e[0], (Long) e[1]))
            .forEach(team -> teamData.put(team.getTeamName(), team));

            em.createQuery("SELECT m.team2, count(*) FROM Match m GROUP BY m.team2", Object[].class)
            .getResultList()
            .stream()
            .forEach(e ->{
                Team team = teamData.get((String)e[0]);
                team.setTotalMatches(team.getTotalMatches()+(Long)e[1]);
            });
            
            
            // set the total wins of a team 
            em.createQuery("SELECT m.matchWinner, count(*) FROM Match m GROUP BY m.matchWinner", Object[].class)
            .getResultList()
            .forEach(e -> {
                Team team = teamData.get((String) e[0]);
                if (team != null) {
                    team.setTotalWins((Long) e[1]);
                }
            });
            
            // persists it to the database using the EntityManager
            teamData.values().forEach(team -> em.persist(team));
        }
        
        
        
    }

}
