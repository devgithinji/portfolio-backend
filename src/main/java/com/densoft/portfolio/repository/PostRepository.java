package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Integer> {
    Optional<Post> findPostByTitle(String title);

    Optional<Post> findPostBySlug(String slug);

    @Query("SELECT p FROM Post p WHERE p.title = :title AND p.id <> :postId")
    Optional<Post> findPostWithMatchingTitle(@Param("title") String title, @Param("postId") Integer postId);

    @Query("SELECT p FROM Post p WHERE LOWER(p.tag.name) = LOWER(:tag)")
    Page<Post> findPostsByTag(Pageable pageable, @Param("tag") String tag);

    @Query("SELECT p FROM Post p WHERE LOWER(p.title) LIKE %:keyword% OR LOWER(p.tag.name) LIKE %:keyword%")
    Page<Post> findPostsByKeyWord(Pageable pageable, @Param("keyword") String keyword);

    @Query(value = "SELECT * FROM posts ORDER BY RAND() LIMIT :limit", nativeQuery = true)
    List<Post> findRandomPosts(@Param("limit") Integer limit);


}
