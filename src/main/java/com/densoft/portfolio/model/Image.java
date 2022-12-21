package com.densoft.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import javax.persistence.*;

@Entity
@Data
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(nullable = false, length = 60)
    private String path;

    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonIgnoreProperties({"images"})
    private Post post;

    public Image(String path, Post post) {
        this.path = path;
        this.post = post;
    }
}
