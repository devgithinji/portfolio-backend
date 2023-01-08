package com.densoft.portfolio.dto;

import com.densoft.portfolio.model.Tag;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.HashSet;
import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ProjectResponse {

    private Integer id;
    private String name;

    private String description;

    private String siteLink;

    private String repoLink;

    private String image;
    private boolean published;

    private Set<Tag> tags = new HashSet<>();
}
