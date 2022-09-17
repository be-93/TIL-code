package com.cus.springbatch.job;

import com.cus.springbatch.domain.User;
import com.cus.springbatch.domain.UserHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class HistoryJobConfiguration {
  private final String JOB_NAME = "historyJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final int chunkSize = 3;

  @Bean(name = JOB_NAME)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(step(null))
        .build();
  }

  @Bean
  @JobScope
  public Step step(@Value("#{jobParameters[version]}") String version) {
    return stepBuilderFactory.get(JOB_NAME + "_step")
        .<User, UserHistory>chunk(chunkSize)
        .reader(reader())
        .processor(processor())
        .writer(writer())
        .build();
  }

  @Bean
  public JpaPagingItemReader reader() {
    return new JpaPagingItemReaderBuilder<UserHistory>()
        .name(JOB_NAME + "_reader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(chunkSize)
        .queryString("SELECT u FROM User u")
        .build();
  }

  @Bean
  public ItemProcessor<User, UserHistory> processor() {
    return item -> new UserHistory(item.getName(), item.getAge());
  }

  @Bean
  public ItemWriter<UserHistory> writer() {
    return new JpaItemWriterBuilder<UserHistory>()
        .entityManagerFactory(entityManagerFactory)
        .usePersist(true)
        .build();
  }

}
