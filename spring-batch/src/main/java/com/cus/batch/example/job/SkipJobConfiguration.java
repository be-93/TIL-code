package com.cus.batch.example.job;

import com.cus.batch.example.domain.user.User;
import com.cus.batch.example.domain.user.UserHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
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
public class SkipJobConfiguration {
  private final String JOB_NAME = "skipJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final int chunkSize = 3;
  private final int skipSize = 3;

  @Bean(name = JOB_NAME)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(step(null))
        .build();
  }

  @Bean(name = JOB_NAME + "_step")
  @JobScope
  public Step step(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return stepBuilderFactory.get(JOB_NAME + "_step")
        .<User, UserHistory>chunk(chunkSize)
        .reader(reader(null))
        .processor(processor())
        .writer(writer())
        .faultTolerant()
        .skip(RuntimeException.class)
        .skipLimit(skipSize)
        .build();
  }

  @Bean(name = JOB_NAME + "_reader")
  @StepScope
  public JpaPagingItemReader reader(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return new JpaPagingItemReaderBuilder<UserHistory>()
        .name(JOB_NAME + "_reader")
        .entityManagerFactory(entityManagerFactory)
        .pageSize(chunkSize)
        .queryString("SELECT u FROM User u")
        .build();
  }

  @Bean(name = JOB_NAME + "_processor")
  public ItemProcessor<User, UserHistory> processor() {
    return item -> {
      if (item.getAge() == 5) {
        log.error("processor error");
        throw new RuntimeException();
      }
      return new UserHistory(item.getName(), item.getAge());
    };
  }

  @Bean(name = JOB_NAME + "_writer")
  public ItemWriter<UserHistory> writer() {
    return new JpaItemWriterBuilder<UserHistory>()
        .entityManagerFactory(entityManagerFactory)
        .build();
  }
}
