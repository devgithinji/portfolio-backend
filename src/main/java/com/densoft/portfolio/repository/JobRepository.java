package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Job;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JobRepository extends JpaRepository<Job, Integer> {

    List<Job> findAllByOrderByIdDesc();
}
