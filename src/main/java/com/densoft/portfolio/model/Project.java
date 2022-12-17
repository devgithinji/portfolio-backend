package com.densoft.portfolio.model;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project extends BaseEntity {
    private String name;
    private String description;
    private String siteLink;
    private String repoLink;
    private String image;
    private String techStack;
}
