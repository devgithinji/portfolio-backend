package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ImageRepository extends JpaRepository<Image, Integer> {
}
