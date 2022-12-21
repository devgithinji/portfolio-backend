package com.densoft.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

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

    @Column(columnDefinition = "boolean default false")
    private Boolean published;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {
            CascadeType.PERSIST,
            CascadeType.DETACH,
            CascadeType.MERGE,
            CascadeType.REFRESH})
    @JoinColumn(name = "tag_id")
    @JsonIgnoreProperties({"posts", "projects"})
    private Tag tag;

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JsonIgnoreProperties({"post"})
    private Set<Image> images = new HashSet<>();


    public Post(String title, String slug, String content) {
        this.title = title;
        this.slug = slug;
        this.content = content;
    }

}
