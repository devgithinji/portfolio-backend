package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.validateStringLIst.ValidStringList;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
public class JobDTO {

    @NotBlank(message = "institution name is required")
    @Size(max = 30, message = "institution name should be less than 30 characters")
    private String institution;
    @NotBlank(message = "title is required")
    @Size(max = 30, message = "title should be less than 30 characters")
    private String title;
    @NotBlank(message = "description is required")
    @Size(max = 200, message = "description should be less than 200 characters")
    private String description;
    @ValidStringList(message = "achievements are required")
    private List<String> achievements;
    @NotBlank(message = "duration is required")
    private String durationRange;
}
