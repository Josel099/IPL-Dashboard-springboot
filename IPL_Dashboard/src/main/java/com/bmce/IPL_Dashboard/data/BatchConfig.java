package com.bmce.IPL_Dashboard.data;

import com.bmce.IPL_Dashboard.model.Match;


import org.springframework.batch.core.Step;

import org.springframework.batch.core.job.builder.JobBuilder;


import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

public class BatchConfig {

//public JobBuilderFactory jobBuilderFactory;

    //refering  field names from matchinput
private final String[] FIELD_NAMES = new String[]{

           "id","city","date","player_of_match","venue","neutral_venue","team1","team2","toss_winner","toss_decision","winner","result","result_margin","eliminator","method","umpire1","umpire2"
    };
    @Bean
    public FlatFileItemReader<MatchInput> reader() {
        return new FlatFileItemReaderBuilder<MatchInput>()
                .name("MatchItemReader")
                .resource(new ClassPathResource("match-data.csv"))
                .delimited()
                .names(FIELD_NAMES)
                .targetType(MatchInput.class)
                .build();
    }

    @Bean
    public MatchDataProcessor processor() {
        return new MatchDataProcessor();
    }

    @Bean
    public JdbcBatchItemWriter<Match> writer(DataSource dataSource) {
        return new JdbcBatchItemWriterBuilder<Match>()
                .sql("INSERT INTO match (id,city,date,player_of_match,venue,team1,team2,toss_winner,toss_decision,match_winner,result,result_margin,umpire1,umpire2) "
                        + "VALUES ( :id, :city, :date, :player_of_match, :venue, :team1, :team2, :toss_winner, :toss_decision, :match_winner, :result, :result_margin, :umpire1, :umpire2)")
                .dataSource(dataSource)
                .beanMapped()
                .build();
    }


    // next session

    @Bean
    public BatchProperties.Job importUserJob(JobRepository jobRepository, Step step1, JobCompletionNotificationListener listener) {
        return (BatchProperties.Job) new JobBuilder("importUserJob", jobRepository)
                .listener(listener)
                .start(step1)
                .build();
    }

    @Bean
    public Step step1(JobRepository jobRepository, DataSourceTransactionManager transactionManager,
                      FlatFileItemReader<MatchInput> reader, MatchDataProcessor processor, JdbcBatchItemWriter<Match> writer) {
        return new StepBuilder("step1", jobRepository)
                .<MatchInput, Match> chunk(3, transactionManager)
                .reader(reader)
                .processor(processor)
                .writer(writer)
                .build();
    }

}