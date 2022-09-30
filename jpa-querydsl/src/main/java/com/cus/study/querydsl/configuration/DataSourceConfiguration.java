package com.cus.study.querydsl.configuration;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

@Configuration
@EnableConfigurationProperties
@RequiredArgsConstructor
public class DataSourceConfiguration {
  private static final String BASE_PACKAGE = "com.cus.study.querydsl.domain";
  @Primary
  @Bean
  @ConfigurationProperties("spring.datasource.hikari")
  public DataSource dataSource() {
    return DataSourceBuilder.create()
        .type(HikariDataSource.class)
        .build();
  }

  @Primary
  @Bean
  public LocalContainerEntityManagerFactoryBean entityManagerFactory(EntityManagerFactoryBuilder builder, DataSource dataSource) {
    return builder.dataSource(dataSource)
        .packages(BASE_PACKAGE)
        .build();
  }

  @Primary
  @Bean(name = "basicTransactionManager")
  public PlatformTransactionManager transactionManager(EntityManagerFactory entityManagerFactory) {
    return new JpaTransactionManager(entityManagerFactory);
  }

  @Bean
  public JPAQueryFactory jpaQueryFactory(EntityManager entityManager) {
    return new JPAQueryFactory(entityManager);
  }
}
