package com.cus.batch.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobGracefulShutdownListener implements JobExecutionListener {
  private final JobExecutionShutdownDefinition shutdownDefinition;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    shutdownDefinition.registry(jobExecution);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    shutdownDefinition.delete(jobExecution);
  }
}
