package com.cus.batch.example.job;

import com.cus.batch.example.listener.JobGracefulShutdownListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.JobScope;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class ShutDownJobConfiguration {
  private final String JOB_NAME = "shutDownJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;
  private final JobGracefulShutdownListener jobGracefulShutdownListener;

  @Bean(name = JOB_NAME)
  public Job job() {
    return jobBuilderFactory.get(JOB_NAME)
        .start(step(null))
        .listener(jobGracefulShutdownListener)
        .build();
  }

  @Bean(name = JOB_NAME + "_step")
  @JobScope
  public Step step(@Value("#{jobParameters[requestDate]}") String version) {
    return stepBuilderFactory.get(JOB_NAME + "_step")
        .tasklet((contribution, chunkContext) -> {
          log.info("=========================================");
          log.info("=====================> step {} ", version);
          log.info("=========================================");
          return RepeatStatus.FINISHED;
        }).build();
  }
}
