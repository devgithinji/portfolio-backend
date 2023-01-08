package com.densoft.portfolio.repository;

import com.densoft.portfolio.model.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface TagRepository extends JpaRepository<Tag, Integer> {
    Optional<Tag> findByName(String name);

    @Query("SELECT t from Tag  t WHERE t.name = :title AND t.id <> :tagId")
    Optional<Tag> findTagWithMatchingTitle(@Param("title") String title, @Param("tagId") Integer tagIId);
}
