package com.densoft.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Data
@Table(name = "images")
@NoArgsConstructor
public class Image extends BaseEntity {
    @Column(nullable = false, length = 200)
    private String path;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonIgnore
    private Post post;

    public Image(String path, Post post) {
        this.path = path;
        this.post = post;
    }

}
