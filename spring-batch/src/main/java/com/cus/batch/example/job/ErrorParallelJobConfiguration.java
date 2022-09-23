package com.cus.batch.example.job;

import com.cus.batch.example.domain.user.User;
import com.cus.batch.example.domain.user.UserBackUp;
import com.cus.batch.example.domain.user.UserHistory;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.database.JpaPagingItemReader;
import org.springframework.batch.item.database.builder.JpaItemWriterBuilder;
import org.springframework.batch.item.database.builder.JpaPagingItemReaderBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

import javax.persistence.EntityManagerFactory;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ErrorParallelJobConfiguration {

  private final String JOB_NAME = "errorParallelJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final EntityManagerFactory entityManagerFactory;
  private final int chunkSize = 2;

  @Bean(name = JOB_NAME)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(parallelFlow(null))
        .end()
        .build();
  }

  @Bean(name = JOB_NAME + "_parallelFlow")
  @JobScope
  public Flow parallelFlow(@Value("#{jobParameters[requestDate]}") String requestDate) {
    return new FlowBuilder<Flow>(JOB_NAME + "_parallelFlow")
        .split(new SimpleAsyncTaskExecutor())
        .add(flow1(), flow2())
        .build();
  }

  @Bean(name = JOB_NAME + "_flow1")
  public Flow flow1() {
    return new FlowBuilder<Flow>(JOB_NAME + "_flow1")
        .start(step1())
        .build();
  }

  @Bean(name = JOB_NAME + "_flow2")
  public Flow flow2() {
    return new FlowBuilder<Flow>(JOB_NAME + "_flow2")
        .start(step2())
        .build();
  }

  @Bean(name = JOB_NAME + "_step1")
  public Step step1() {
    return stepBuilderFactory.get(JOB_NAME + "_step1")
        .<User, UserHistory>chunk(chunkSize)
        .reader(reader(null))
        .processor(processor1())
        .writer(writer1())
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

  @Bean(name = JOB_NAME + "_processor1")
  public ItemProcessor<User, UserHistory> processor1() {
    return item -> {
      if (item.getAge() == 2) {
        log.error("processor1 {} : {}", item.getAge() , item.getName());
        throw new RuntimeException("processor error was exception");
      }
      return new UserHistory(item.getName(), item.getAge());
    };
  }

  @Bean(name = JOB_NAME + "_writer1")
  public ItemWriter<UserHistory> writer1() {
    return new JpaItemWriterBuilder<UserHistory>()
        .entityManagerFactory(entityManagerFactory)
        .build();
  }

  @Bean(name = JOB_NAME + "_step2")
  public Step step2() {
    return stepBuilderFactory.get(JOB_NAME + "_step2")
        .<User, UserBackUp>chunk(chunkSize)
        .reader(reader(null))
        .processor(processor2())
        .writer(writer2())
        .build();
  }

  @Bean(name = JOB_NAME + "_processor2")
  public ItemProcessor<User, UserBackUp> processor2() {
    return item -> {
      long millis = 1000;

      try {
        Thread.sleep(millis);
      } catch (InterruptedException e) {
        throw new RuntimeException(e);
      }

      log.info("processor2 sleep : {}", millis);
      return new UserBackUp(item.getName(), item.getAge());
    };
  }

  @Bean(name = JOB_NAME + "_writer2")
  public ItemWriter<UserBackUp> writer2() {
    return new JpaItemWriterBuilder<UserBackUp>()
        .entityManagerFactory(entityManagerFactory)
        .build();
  }

}
