package com.cus.batch.example.user.domain;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBackUpRepository extends JpaRepository<UserBackUp, Long> {
}