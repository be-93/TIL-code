package com.cus.springbatch.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Entity
@Table(name = "user_master")
@NoArgsConstructor
@AllArgsConstructor
public class User {
  @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private int age;
}
