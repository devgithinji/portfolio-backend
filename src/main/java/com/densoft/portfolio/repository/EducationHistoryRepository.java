package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Education;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EducationHistoryRepository extends JpaRepository<Education, Integer> {
}
