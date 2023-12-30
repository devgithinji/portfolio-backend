package com.densoft.portfolio.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static com.fasterxml.jackson.annotation.JsonProperty.Access;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "jobs")
public class Job extends BaseEntity implements Comparable<Job> {


    @Column(length = 100, nullable = false)
    private String institution;

    @Column(length = 100, nullable = false)
    private String title;

    @Column(length = 300, nullable = false)
    private String description;

    @Column(length = 1000, nullable = false)
    @JsonProperty(access = Access.WRITE_ONLY)
    private String achievements;

    @Column(length = 1000, nullable = false, name = "duration_range")
    private String durationRange;

    @Transient
    private List<String> achievementsList;

    public Job(String institution, String title, String description, String achievements, String durationRange) {
        this.institution = institution;
        this.title = title;
        this.description = description;
        this.achievements = achievements;
        this.durationRange = durationRange;
    }


    public List getAchievementsList() throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readValue(this.achievements, List.class);
    }

    @Override
    public int compareTo(Job otherjob) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM yyyy");
        try {

            Date thisStartDate = dateFormat.parse(durationRange.split("-")[0].strip());
            Date otherStartDate = dateFormat.parse(otherjob.durationRange.split("-")[0].strip());

            return thisStartDate.compareTo(otherStartDate);

        } catch (Exception e) {
            System.out.println(e.getMessage());
            return 0;
        }

    }
}
