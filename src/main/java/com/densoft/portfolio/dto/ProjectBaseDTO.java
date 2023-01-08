package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.tagContraint.TagConstraint;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public abstract class ProjectBaseDTO {

    @NotBlank(message = "project name is required")
    @Size(min = 5, max = 50, message = "name should be between 5 to 50 characters")
    private String name;
    @NotBlank(message = "project description is required")
    @Size(min = 5, max = 200, message = "description should be between 5 to 100 characters")
    private String description;
    @NotBlank(message = "project link is required")
    private String siteLink;
    @NotBlank(message = "project repo link is required")
    private String repoLink;
    @TagConstraint
    private String[] tags;



}
