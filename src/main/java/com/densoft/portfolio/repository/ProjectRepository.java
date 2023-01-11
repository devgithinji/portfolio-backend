package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ProjectRepository extends JpaRepository<Project, Integer> {

    Page<Project> findProjectByTags_Name(Pageable pageable, String tag);

    @Query(value = "SELECT * FROM projects WHERE site_link != 'empty' ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Project> findRandomProjects(@Param("limit") Integer limit);
}
