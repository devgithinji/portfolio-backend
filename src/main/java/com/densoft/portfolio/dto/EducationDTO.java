package com.densoft.portfolio.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EducationDTO {

    @NotBlank(message = "institution name is required")
    @Size(max = 40, message = "institution name maximum size: 40 characters")
    private String institution;
    @NotBlank(message = "level is required")
    @Size(max = 80, message = "level maximum size: 80 characters")
    private String level;
    @NotBlank(message = "qualification is required")
    @Size(max = 80, message = "qualification maximum size: 80 characters")
    private String qualification;
    @NotBlank(message = "award is required")
    @Size(max = 80, message = "award maximum size: 80 characters")
    private String award;
    @NotBlank(message = "duration is required")
    private String durationRange;
}
