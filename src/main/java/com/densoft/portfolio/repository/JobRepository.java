package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JobRepository extends JpaRepository<Job, Integer> {
}
