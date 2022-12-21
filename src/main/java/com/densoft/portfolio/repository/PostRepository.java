package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findPostByTitle(String title);

    Optional<Post> findPostBySlug(String slug);

    @Query("SELECT p FROM Post p WHERE p.title = :title AND p.id <> :postId")
    Optional<Post> findPostWithMatchingTitle(@Param("title") String title, @Param("postId") Integer postId);


}
