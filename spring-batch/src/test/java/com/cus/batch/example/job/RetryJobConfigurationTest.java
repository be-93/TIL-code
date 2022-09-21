package com.cus.batch.example.job;

import com.cus.batch.TestBatchConfiguration;
import com.cus.batch.example.domain.user.User;
import com.cus.batch.example.domain.user.UserBackUp;
import com.cus.batch.example.domain.user.UserBackUpRepository;
import com.cus.batch.example.domain.user.UserHistory;
import com.cus.batch.example.domain.user.UserHistoryRepository;
import com.cus.batch.example.domain.user.UserRepository;
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
@SpringBootTest(classes = {RetryJobConfiguration.class, TestBatchConfiguration.class})
class RetryJobConfigurationTest {

  @Autowired
  private JobLauncherTestUtils jobLauncherTestUtils;
  @Autowired
  private UserRepository userRepository;
  @Autowired
  private UserHistoryRepository userHistoryRepository;
  @Autowired
  private UserBackUpRepository userBackUpRepository;

  @AfterEach
  void tearDown() {
    userRepository.deleteAll();
    userHistoryRepository.deleteAll();
  }

  @Test
  public void 재시도_테스트() throws Exception {
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
    List<UserBackUp> backUps = userBackUpRepository.findAll();

    assertAll(
        () -> assertThat(actual.getExitStatus().getExitCode()).isEqualTo(ExitStatus.FAILED.getExitCode()),
        () -> assertThat(histories.size()).isEqualTo(3),
        () -> assertThat(backUps).isEmpty()
    );
  }
}