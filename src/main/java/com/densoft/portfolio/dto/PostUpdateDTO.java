package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import com.densoft.portfolio.validators.tagContraint.SingleTagValidator;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostUpdateDTO {

    @NotBlank(message = "Post title is required")
    @Size(min = 3, max = 100, message = "Post title should be (3 char min, 100 char max)")
    private String title;
    @NotBlank(message = "Post content is required")
    @Size(max = 20000, message = "Post content is too long")
    private String content;

    @SingleTagValidator
    private String tag;

    @ValidFile(fileType = FileType.IMAGE, message = "image required type: (png/jpeg/jpg) max size: 1MB", maxSize = 1)
    MultipartFile image;

    public PostUpdateDTO(String title, String content, String tag) {
        this.title = title;
        this.content = content;
        this.tag = tag;
    }
}


