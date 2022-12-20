package com.densoft.portfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "images")
public class Image extends BaseEntity {
    @Column(nullable = true, length = 60)
    private String path;
}
