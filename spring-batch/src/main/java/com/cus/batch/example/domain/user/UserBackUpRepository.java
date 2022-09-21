package com.cus.batch.example.domain.user;

import org.springframework.data.jpa.repository.JpaRepository;

public interface UserBackUpRepository extends JpaRepository<UserBackUp, Long> {
}