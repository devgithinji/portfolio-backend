package com.densoft.portfolio.model;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.utils.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.*;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.fasterxml.jackson.annotation.JsonProperty.Access.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
public class Post extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true)
    private String title;

    @Column(nullable = false, length = 100)
    private String slug;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(name = "blog_url", length = 100)
    private String blogUrl;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinTable(
            name = "post_tags",
            joinColumns = @JoinColumn(name = "post_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    private Set<Tag> tags = new HashSet<>();
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean published;

    public Post(String title, String slug, String content, Boolean published) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.published = published;
    }

    public void addTag(Tag newTag) {
        this.tags.add(newTag);
        newTag.getPosts().add(this);
    }

}
