package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    List<Project> findProjectByTags_Name(String tag);
}
