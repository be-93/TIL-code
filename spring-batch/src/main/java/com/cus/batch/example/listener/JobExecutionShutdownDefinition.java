package com.cus.batch.example.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class JobExecutionShutdownDefinition implements ApplicationListener<ContextClosedEvent> {
  private final long AWAIT_TIME = 500;
  private final List<JobExecution> EXECUTE_JOB_POOL = new ArrayList<>();

  @Override
  public void onApplicationEvent(ContextClosedEvent event) {
    while (!EXECUTE_JOB_POOL.isEmpty()) {
      try {
        log.info("waiting for job");
        Thread.sleep(AWAIT_TIME);
      } catch (InterruptedException e) {
        log.error("spring batch graceful shutdown waiting error!!");
        Thread.interrupted();
      }
    }
    log.info("spring batch graceful shutdown");
    return;
  }

  public void registry(JobExecution jobExecution) {
    EXECUTE_JOB_POOL.add(jobExecution);
  }

  public void delete(JobExecution jobExecution) {
    EXECUTE_JOB_POOL.remove(jobExecution);
  }
}
