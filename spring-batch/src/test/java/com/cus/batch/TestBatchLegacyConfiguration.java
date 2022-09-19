package com.cus.batch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.test.JobLauncherTestUtils;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@EnableAutoConfiguration
@EnableBatchProcessing
public class TestBatchLegacyConfiguration {
  @Bean
  public JobLauncherTestUtils jobLauncherTestUtils() { // (2)
    return new JobLauncherTestUtils();
  }
}
