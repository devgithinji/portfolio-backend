package com.densoft.portfolio.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "services")
public class Service extends BaseEntity{

    @Column(nullable = false, length = 20)
    private String name;
    @Column(nullable = false, length = 100)
    private String description;
    @Column(nullable = false, length = 40)
    private String photo;
}
