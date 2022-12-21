package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import com.densoft.portfolio.validators.tagContraint.TagConstraint;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ProjectDTO {

    @NotBlank(message = "project name is required")
    @Size(min = 5, max = 50, message = "name should be between 5 to 50 characters")
    private String name;
    @NotBlank(message = "project description is required")
    @Size(min = 5, max = 100, message = "description should be between 5 to 100 characters")
    private String description;
    @NotBlank(message = "project link is required")
    private String siteLink;
    @NotBlank(message = "project repo link is required")
    private String repoLink;
    @TagConstraint
    private String[] tags;

    @ValidFile(fileType = FileType.IMAGE, message = "image required type: (png/jpeg/jpg) max size: 1MB", maxSize = 1)
    MultipartFile image;

}
