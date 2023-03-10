package com.densoft.portfolio.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Data
@NoArgsConstructor
@Entity
@Table(name = "education")
public class Education extends BaseEntity {

    @Column(length = 100, nullable = false)
    private String institution;
    @Column(length = 100, nullable = false)
    private String level;
    @Column(length = 100, nullable = false)
    private String qualification;
    @Column(length = 100, nullable = false)
    private String award;
    @Column(length = 100, nullable = false, name = "duration_range")
    private String durationRange;


    public Education(String institution, String level, String qualification, String award, String durationRange) {
        this.institution = institution;
        this.level = level;
        this.qualification = qualification;
        this.award = award;
        this.durationRange = durationRange;
    }
}
