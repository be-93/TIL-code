package com.cus.batch.example.job;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.task.SimpleAsyncTaskExecutor;

@Slf4j
@Configuration
@RequiredArgsConstructor
public class SimpleParallelJobConfiguration {

  private final String JOB_NAME = "simpleParallelJob";
  private final JobBuilderFactory jobBuilderFactory;
  private final StepBuilderFactory stepBuilderFactory;

  @Bean(name = JOB_NAME)
  public Job job() {
    Flow flow1 = new FlowBuilder<Flow>(JOB_NAME + "_flow1")
        .start(step1())
        .build();

    Flow flow2 = new FlowBuilder<Flow>(JOB_NAME + "_flow2")
        .start(step2())
        .build();

    Flow flow3 = new FlowBuilder<Flow>(JOB_NAME + "_flow3")
        .start(step3())
        .build();

    Flow parallelStepFlow = new FlowBuilder<Flow>(JOB_NAME + "_parallelFlow")
        .split(new SimpleAsyncTaskExecutor())
        .add(flow1, flow2, flow3)
        .build();

    return jobBuilderFactory.get(JOB_NAME)
        .start(parallelStepFlow)
        .next(step4())
        .build()
        .build();
  }

  @Bean(name = JOB_NAME + "_step1")
  public Step step1() {
    return stepBuilderFactory.get(JOB_NAME + "_step1")
        .tasklet((contribution, chunkContext) -> {
          log.info("===> step1");
          return RepeatStatus.FINISHED;
        }).build();
  }

  @Bean(name = JOB_NAME + "_step2")
  public Step step2() {
    return stepBuilderFactory.get(JOB_NAME + "_step2")
        .tasklet((contribution, chunkContext) -> {
          Thread.sleep(5000);
          log.info("===> sleep step2");
          return RepeatStatus.FINISHED;
        }).build();
  }

  @Bean(name = JOB_NAME + "_step3")
  public Step step3() {
    return stepBuilderFactory.get(JOB_NAME + "_step3")
        .tasklet((contribution, chunkContext) -> {
          log.info("===> step3");
          return RepeatStatus.FINISHED;
        }).build();
  }

  @Bean(name = JOB_NAME + "_step4")
  public Step step4() {
    return stepBuilderFactory.get(JOB_NAME + "_step4")
        .tasklet((contribution, chunkContext) -> {
          log.info("===> step4");
          return RepeatStatus.FINISHED;
        }).build();
  }

}
