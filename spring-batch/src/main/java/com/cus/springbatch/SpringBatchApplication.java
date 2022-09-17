package com.cus.springbatch;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@EnableBatchProcessing
@SpringBootApplication
public class SpringBatchApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringBatchApplication.class, args);
  }

}
