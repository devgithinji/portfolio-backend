package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findPostByTitle(String title);
}
