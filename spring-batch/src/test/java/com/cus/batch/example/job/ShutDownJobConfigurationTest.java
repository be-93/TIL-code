package com.cus.batch.example.job;

import com.cus.batch.TestBatchConfiguration;
import com.cus.batch.example.listener.JobExecutionShutdownDefinition;
import com.cus.batch.example.listener.JobGracefulShutdownListener;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBatchTest
@SpringBootTest(classes = {ShutDownJobConfiguration.class, TestBatchConfiguration.class, JobGracefulShutdownListener.class, JobExecutionShutdownDefinition.class})
class ShutDownJobConfigurationTest {
  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;

  @Test
  public void 셧다운_테스트() throws Exception {
    //given
    JobParameters jobParameters = new JobParametersBuilder()
        .addString("version", "1")
        .toJobParameters();

    //when
    JobExecution jobExecution = null;
    try {
      jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    //then
    JobExecution actual = jobExecution;

    assertThat(actual).isNull();
  }
}
