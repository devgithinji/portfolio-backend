package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TagRepository extends JpaRepository<Tag, Integer> {
}
