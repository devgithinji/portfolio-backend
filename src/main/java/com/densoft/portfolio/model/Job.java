package com.densoft.portfolio.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String institution;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    private String achievements;

    @Column(length = 1000, nullable = false, name = "duration_range")
    private String durationRange;

    public Job(String institution, String title, String description, String achievements, String durationRange) {
        this.institution = institution;
        this.title = title;
        this.description = description;
        this.achievements = achievements;
        this.durationRange = durationRange;
    }
}
