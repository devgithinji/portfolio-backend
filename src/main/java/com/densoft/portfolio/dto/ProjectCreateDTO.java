package com.densoft.portfolio.dto;

import com.densoft.portfolio.validators.fileType.FileType;
import com.densoft.portfolio.validators.fileType.ValidFile;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ProjectCreateDTO extends ProjectBaseDTO {

    @ValidFile(fileType = FileType.IMAGE, message = "image required type: (png/jpeg/jpg) max size: 1MB", maxSize = 1, isRequired = true)
    MultipartFile image;
}
