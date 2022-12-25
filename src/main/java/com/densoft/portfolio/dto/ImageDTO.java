package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Data
public class ImageDTO {

    @NotBlank(message = "Post Id is required")
    @Pattern(regexp = "[0-9]+", message = "invalid post Id")
    private String postId;
    @ValidFile(fileType = FileType.IMAGE, message = "image type: (png, jpeg, jpg) size: 1MB", maxSize = 1, isRequired = true)
    private MultipartFile image;
}
