package com.bmce.IPL_Dashboard.data;

import com.bmce.IPL_Dashboard.model.Match;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.item.database.JdbcBatchItemWriter;
import org.springframework.batch.item.database.builder.JdbcBatchItemWriterBuilder;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.core.io.ClassPathResource;

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

}
