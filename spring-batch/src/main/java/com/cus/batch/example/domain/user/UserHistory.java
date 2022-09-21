package com.cus.batch.example.domain.user;

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
@Table(name = "user_history")
@NoArgsConstructor
@AllArgsConstructor
public class UserHistory {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private long id;
  private String name;
  private int age;

  public UserHistory(String name, int age) {
    this.name = name;
    this.age = age;
  }
}
