package com.densoft.portfolio.model;

import com.densoft.portfolio.dto.PostDTO;
import com.densoft.portfolio.utils.Util;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.*;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;


import static com.fasterxml.jackson.annotation.JsonProperty.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Post extends BaseEntity {

    @Column(nullable = false, length = 100, unique = true)
    private String title;

    @Column(nullable = false, length = 100)
    private String slug;
    @Column(nullable = false, length = 3000)
    private String content;
    @Column(name = "medium_url", length = 100)
    private String mediumUrl;
    @Column(nullable = false, length = 100)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String tags;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private Boolean published;

    @Transient
    private String[] tagsList;


    public Post(String title, String slug, String content, String tags, Boolean published) {
        this.title = title;
        this.slug = slug;
        this.content = content;
        this.tags = tags;
        this.published = published;
    }

    public String[] getTagsList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(tags, String[].class);
    }

    public void setTags(String tags) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        this.tags = objectMapper.writeValueAsString(tags);
    }

    public static Post createPost(PostDTO postDTO) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return new Post(postDTO.getTitle(),
                Util.generateSlug(postDTO.getTitle()),
                postDTO.getContent(),
                objectMapper.writeValueAsString(postDTO.getTags()),
                true);
    }
}
