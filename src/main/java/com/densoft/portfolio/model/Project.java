package com.densoft.portfolio.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "projects")
@Getter
@Setter
@NoArgsConstructor
public class Project extends BaseEntity {
    @Column(nullable = false, length = 50)
    private String name;
    @Column(nullable = false, length = 100)
    private String description;
    @Column(nullable = false, length = 50, name = "site_link")
    private String siteLink;
    @Column(nullable = false, length = 50, name = "repo_ink")
    private String repoLink;
    @Column(nullable = false, length = 50)
    private String image;
    @Column(nullable = false, columnDefinition = "boolean default false")
    private boolean published;
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH})
    @JoinTable(name = "project_tags",
            joinColumns = @JoinColumn(name = "project_id", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "id"))
    @JsonIgnoreProperties({"projects", "posts"})
    private Set<Tag> tags = new HashSet<>();


    public Project(String name, String description, String siteLink, String repoLink, String image, boolean published) {
        this.name = name;
        this.description = description;
        this.siteLink = siteLink;
        this.repoLink = repoLink;
        this.image = image;
        this.published = published;
    }

    public void addTag(Tag tag){
        this.tags.add(tag);
        tag.getProjects().add(this);
    }
}
