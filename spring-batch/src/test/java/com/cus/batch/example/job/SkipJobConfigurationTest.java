package com.cus.batch.example.job;

import com.cus.batch.TestBatchConfiguration;
import com.cus.batch.example.user.domain.User;
import com.cus.batch.example.user.domain.UserHistory;
import com.cus.batch.example.user.domain.UserHistoryRepository;
import com.cus.batch.example.user.domain.UserRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.batch.test.context.SpringBatchTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@SpringBatchTest
@SpringBootTest(classes = {SkipJobConfiguration.class, TestBatchConfiguration.class})
class SkipJobConfigurationTest {
  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserHistoryRepository userHistoryRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    userHistoryRepository.deleteAll();
  }

  @Test
  public void 스킵_테스트() throws Exception {
    //given
    for (int i = 0; i < 10; i++) {
      userRepository.save(User.builder()
          .age(i)
          .name(i + " 번째")
          .build()
      );
    }

    JobParameters jobParameters = new JobParametersBuilder()
        .addString("requestDate", LocalDateTime.now().toString())
        .toJobParameters();

    //when
    JobExecution jobExecution = null;
    try {
      jobExecution = jobLauncherTestUtils.launchJob(jobParameters);
    } catch (Exception e) {
    }

    //then
    JobExecution actual = jobExecution;
    List<UserHistory> histories = userHistoryRepository.findAll();

    assertAll(
        () -> assertThat(actual.getExitStatus().getExitCode()).isEqualTo(ExitStatus.COMPLETED.getExitCode()),
        () -> assertThat(histories.size()).isEqualTo(9)
    );
  }
}